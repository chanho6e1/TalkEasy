package com.talkeasy.server.service.chat;

import com.google.gson.Gson;
import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.common.exception.ArgumentMismatchException;
import com.talkeasy.server.common.exception.ResourceAlreadyExistsException;
import com.talkeasy.server.common.exception.ResourceNotFoundException;
import com.talkeasy.server.domain.alarm.Alarm;
import com.talkeasy.server.domain.app.UserAppToken;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.domain.chat.ChatRoom;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.domain.chat.LastChat;
import com.talkeasy.server.dto.alarm.RequestSosAlarmDto;
import com.talkeasy.server.dto.chat.ChatRoomDto;
import com.talkeasy.server.dto.chat.ChatRoomListDto;
import com.talkeasy.server.dto.chat.UserInfo;
import com.talkeasy.server.service.firebase.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final MongoTemplate mongoTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitAdmin rabbitAdmin;
    private final AmqpAdmin amqpAdmin;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final Gson gson;

    public CommonResponse createRoom(String user1, String user2) throws IOException {

        //user1이 로그인한 사용자, user2가 대상자

        ChatRoom existRoom = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("users").all(user1, user2)), ChatRoom.class)).orElse(null);

        if (existRoom != null) {
            throw new ResourceAlreadyExistsException("이미 생성된 채팅방 입니다");
        }

        ChatRoom chatRoom = new ChatRoom(new String[]{user1, user2}, "hihi", LocalDateTime.now().toString());
        ChatRoom chatRoomDto = mongoTemplate.insert(chatRoom, "chat_room");

        createQueue(new ChatRoomDto(chatRoomDto));

//        doCreateRoomChat(chatRoomDto, user1);
//        doCreateRoomChat(chatRoomDto, user2);

        return CommonResponse.of(HttpStatus.CREATED, chatRoom.getId());

    }


    public void doCreateRoomChat(ChatRoom chatRoom, String userId) throws IOException {

        ChatRoomDetail chat = ChatRoomDetail.builder().roomId(chatRoom.getId()).toUserId(userId).fromUserId("admin").msg("채팅방이 생성되었습니다.").created_dt(LocalDateTime.now().toString()).readCnt(0).build();

        doChat(chat);

    }

    public void createQueue(ChatRoomDto chatRoomDto) {

        /* chat.queue*/
        createQueueDetail(chatRoomDto, "chat", chatRoomDto.getFromUserId());
        createQueueDetail(chatRoomDto, "chat", chatRoomDto.getToUserId());

        /* read.queue*/
        createQueueDetail(chatRoomDto, "read", chatRoomDto.getFromUserId());
        createQueueDetail(chatRoomDto, "read", chatRoomDto.getToUserId());

    }

    //queueName: read/chat
    public void createQueueDetail(ChatRoomDto chatRoomDto, String queueName, String userId) {
        String roomId = chatRoomDto.getRoomId();
        String queueId = String.format("%s.queue.%s.%s", queueName, roomId, userId);
        String routingKey = String.format("room.%s.%s", roomId, userId);

        Queue queue = QueueBuilder.durable(queueId).build();
        amqpAdmin.declareQueue(queue);

        Binding binding = BindingBuilder
                .bind(queue)
                .to(new TopicExchange(queueName + ".exchange"))
                .with(routingKey);
        amqpAdmin.declareBinding(binding);
    }


    public ChatRoomDetail convertChat(Message message) {
        String str = new String(message.getBody());
        ChatRoomDetail chat = gson.fromJson(str, ChatRoomDetail.class);

        String newMsg = chat.getMsg();

        Alarm alarm = Alarm.builder().chatId(chat.getId()).readStatus(false).build();
//        Alarm alarm = new Alarm();

//        Alarm alarm1 = Alarm.builder().chatId(chat.getId()).readStatus(false).build();

        if (chat.getType() == 1) { // location :: msg:: 요청 or 결과 or 실패 (REQUEST, RESULT, REJECT)
            if (chat.getStatus() == 0) {// REQUEST
                newMsg = "위치 열람 요청";
            } else if (chat.getStatus() == 1) { // RESULT
//                newMsg = nowMsg[1]; // HH:MM

            } else if (chat.getStatus() == 2) {// REJECT
                newMsg = "요청 응답 없음";
            }
        } else if (chat.getType() == 2) { // SOS
            newMsg = "긴급 도움 요청";
            // 보호자한테만 알림 저장

        }

          /*
                1. 보호자인지 비보호자인지
                2. 타입
                에 따라 담는 메시지가 달라짐
                */
        setAlarmContent(chat, alarm);

        chat.setMsg(newMsg);
        chat.setCreated_dt(LocalDateTime.now().toString());
        chat.setReadCnt(1);


        /*알람을 디비에 저장*/
        log.info("{}", chat);
        return chat;
    }

    private Alarm createAlarm(ChatRoomDetail chat, String content, String fromName, int type, String userId) {
        Alarm alarm = Alarm.builder()
                .chatId(chat.getId())
                .readStatus(false)
                .userId(userId)
                .type(type)
                .content(content)
                .fromName(fromName)
                .build();
        saveAlarm(alarm);
        return alarm;
    }

    private void setAlarmContent(ChatRoomDetail chat, Alarm alarm) {

        Member fromMember = getMemberById(chat.getFromUserId());
        Member toMember = getMemberById(chat.getToUserId());

//        if (chat.getType() == 1) {
//            alarm.setType(1);
//
//            if (chat.getStatus() == 1) {
////            if (chat.getStatus() == 1 && toMember.getRole() == 0) {
//                alarm.setContent(chat.getMsg());
//                alarm.setFromName(fromMember.getName());
//                saveAlarm(alarm);
//            }
//        }

        if (chat.getType() == 2) {
            alarm.setType(2);
            /*00님이 긴급 도움 요청을 하셨습니다*/
            createAlarm(chat, toMember.getName()+"님께서 긴급 도움 요청을 하셨습니다", toMember.getName(), 2, toMember.getId());
//            createAlarm(chat, fromMember.getName()+"이 긴급 도움 요청을 하셨습니다", fromMember.getName(), 2, toMember.getId());
        }
    }

    public String saveAlarm(Alarm alarm) {
        Alarm alarmDomain = mongoTemplate.insert(alarm);
        return alarmDomain.getId();
    }


    public String saveChat(ChatRoomDetail chat) {
        ChatRoomDetail chatRoomDetail = mongoTemplate.insert(chat);

        //마지막 채팅 저장
        saveLastChat(chat);

        return chatRoomDetail.getRoomId();
    }

    public void doChat(ChatRoomDetail chat) throws IOException {

        sendChatMessage(chat, chat.getToUserId());

        PagedResponse<ChatRoomListDto> fromUserList = getChatRoomList(chat.getFromUserId());
        PagedResponse<ChatRoomListDto> toUserList = getChatRoomList(chat.getToUserId());

        rabbitTemplate.convertAndSend("user.exchange", "user." + chat.getToUserId(), gson.toJson(toUserList));
        rabbitTemplate.convertAndSend("user.exchange", "user." + chat.getFromUserId(), gson.toJson(fromUserList));

        /* FCM 알림 - 안드로이드 FCM 연결 시, 주석 풀 것. */
//        Member member = mongoTemplate.findOne(Query.query(Criteria.where("id").is(chat.getFromUserId())), Member.class);
//        UserAppToken userAppToken = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(chat.getToUserId())), UserAppToken.class);
//
//        firebaseCloudMessageService.sendMessageTo(userAppToken.getAppToken(), member.getName(), chat.getMsg()); // String targetToken, String title, String body

    }


    public void sendChatMessage(ChatRoomDetail chat, String toUserId) {
        String routingKey = String.format("room.%s.%s", chat.getRoomId(), toUserId);

        Message msg = MessageBuilder.withBody(gson.toJson(chat).getBytes()).build();
        rabbitTemplate.send("chat.exchange", routingKey, msg);
    }


    public void updateUserInChatRoom(ChatRoom chatRoom, String userId) {
        boolean change = false;
        if (!userId.equals("admin") && !chatRoom.getChatUsers().get(userId).getNowIn()) {
            chatRoom.getChatUsers().get(userId).setNowIn(true);
            change = true;
        }
        if (change) mongoTemplate.save(chatRoom);
    }

    ///////////////////////////////////////

    public PagedResponse<ChatRoomDetail> getChatHistory(String chatRoomId, int offset, int size, String userId) {

        Pageable pageable = PageRequest.of(offset - 1, size, Sort.by(Sort.Direction.ASC, "created_dt"));

        ChatRoom chatRoom = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("id").is(chatRoomId)), ChatRoom.class))
                .orElseThrow(() -> new ResourceNotFoundException("ChatRoom", "chatRoomId", chatRoomId));

        String leaveTime = chatRoom.getChatUsers().get(userId).getLeaveTime() == null ? chatRoom.getDate() : chatRoom.getLeaveTime();

        Query query = new Query(Criteria.where("roomId").is(chatRoomId).and("created_dt").gte(leaveTime)).with(pageable);

        List<ChatRoomDetail> filteredMetaData = Optional.ofNullable(mongoTemplate.find(query, ChatRoomDetail.class)).orElse(Collections.emptyList());


        Page<ChatRoomDetail> metaDataPage = PageableExecutionUtils.getPage(filteredMetaData, pageable, () -> mongoTemplate.count(query.skip(-1).limit(-1), ChatRoomDetail.class));

        return new PagedResponse(HttpStatus.OK, metaDataPage.getContent(), metaDataPage.getTotalPages());

    }


    public PagedResponse<ChatRoomListDto> getChatRoomList(String userId) {
        List<ChatRoomListDto> chatRoomListDtoList = new ArrayList<>();

        List<LastChat> lastChatList = getLastChatList(userId);
        lastChatList.sort((c1, c2) -> c2.getCreated_dt().compareTo(c1.getCreated_dt())); // 최신순으로 정렬

        for (LastChat lastChat : lastChatList) {
            String otherUserId = lastChat.getFromUserId().equals(userId) ? lastChat.getToUserId() : lastChat.getFromUserId();
            Member member = getMemberById(otherUserId);

            if (member != null) {
                ChatRoomListDto chatRoomListDto = new ChatRoomListDto(lastChat);
                chatRoomListDto.setProfile(member.getImageUrl());
                chatRoomListDto.setName(member.getName());

                QueueInformation queueInformation = getQueueInfo(lastChat.getRoomId(), userId);
                if (queueInformation != null) {
                    log.info("queueInfo cnt : {}", queueInformation.getMessageCount());
                    chatRoomListDto.setNoReadCnt(queueInformation.getMessageCount());
                } else {
                    log.warn("queueInformation is null");
                }

                chatRoomListDtoList.add(chatRoomListDto);
            }
        }

        return new PagedResponse(HttpStatus.OK, chatRoomListDtoList, 1);
    }


    public Member getMemberById(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        return Optional.ofNullable(mongoTemplate.findOne(query, Member.class)).orElse(null);
    }

    public QueueInformation getQueueInfo(String roomId, String userId) {
        String queueName = String.format("chat.queue.%s.%s", roomId, userId);
        return rabbitAdmin.getQueueInfo(queueName);
    }

    public void saveLastChat(ChatRoomDetail chat) {

        saveLastChatDetail(chat, chat.getFromUserId());
        saveLastChatDetail(chat, chat.getToUserId());
    }


    public void saveLastChatDetail(ChatRoomDetail chat, String userId) {

        mongoTemplate.remove(Query.query(Criteria.where("roomId").is(chat.getRoomId()).and("userId").is(userId)), LastChat.class);

        LastChat lastChatDto = new LastChat(chat);
        lastChatDto.setUserId(userId);
        mongoTemplate.insert(lastChatDto, "last_chat");
    }

    public List<LastChat> getLastChatList(String userId) {
        return mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)), LastChat.class);
    }


    /*
        1. 언팔로우 or 회원탈퇴 시 채팅방 폭파
    */
    public String deleteRoom(String roomId, String userId) throws IOException {

        Query query = new Query().addCriteria(Criteria.where("id").is(roomId));
        ChatRoom chatRoom = mongoTemplate.findOne(Query.query(Criteria.where("id").is(roomId)), ChatRoom.class);

        /* chat.queue, read.queue 삭제 */

        String toUserId = Arrays.stream(chatRoom.getUsers()).filter(a -> !a.equals(userId)).toArray(String[]::new)[0];

        // 채팅방에 남은 인원이 1명인 경우만 삭제
        mongoTemplate.remove(query, ChatRoom.class); //채팅방 삭제
        mongoTemplate.remove(Query.query(Criteria.where("roomId").is(roomId)), ChatRoomDetail.class); //채팅 내역 삭제
        mongoTemplate.remove(Query.query(Criteria.where("roomId").is(roomId)), LastChat.class); // lastChat 모두 삭제

        /* chat.queue, read.queue 삭제 */
        deleteQueue("chat.queue", chatRoom.getId(), userId);
        deleteQueue("read.queue", chatRoom.getId(), userId);

        deleteQueue("chat.queue", chatRoom.getId(), toUserId);
        deleteQueue("read.queue", chatRoom.getId(), toUserId);

        return roomId;

    }

    private void deleteQueue(String queueName, String roomId, String userId) {
        amqpAdmin.deleteQueue(queueName + "." + roomId + "." + userId);
    }

    public PagedResponse<UserInfo> getUserInfoByRoom(String roomId) {

        ChatRoom chatRoom = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("id").is(roomId)), ChatRoom.class)).orElseThrow(() -> new ResourceNotFoundException("없는 채팅방 입니다"));

        String[] userIds = chatRoom.getUsers();

        List<Member> members = mongoTemplate.find(Query.query(Criteria.where("id").all(userIds)), Member.class);

        List<UserInfo> userInfos = members.stream().map(member -> UserInfo.builder().userId(member.getId()).userName(member.getName()).profileImg(member.getImageUrl()).deleteStatus(member.getDeleteStatus()).build()).collect(Collectors.toList());

        return new PagedResponse(HttpStatus.OK, userInfos, 1);
    }


}