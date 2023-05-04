package com.talkeasy.server.service.chat;

import com.google.gson.Gson;
import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.common.exception.ArgumentMismatchException;
import com.talkeasy.server.common.exception.ResourceAlreadyExistsException;
import com.talkeasy.server.common.exception.ResourceNotFoundException;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.domain.chat.ChatRoom;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.domain.chat.LastChat;
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
    private final AmqpAdmin amqpAdmin;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final Gson gson;

    public String createRoom(String user1, String user2) throws IOException {

        //user1이 로그인한 사용자, user2가 대상자

        ChatRoom existRoom = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("users").in(new String[]{user1, user2})), ChatRoom.class)).orElse(null);

        if (existRoom != null) {
//            throw new ResourceAlreadyExistsException("이미 생성된 채팅방 입니다");

            // nowIn -> true 바꿔주기
            existRoom.getChatUsers().get(user1).setNowIn(true);
            mongoTemplate.save(existRoom);

            return existRoom.getId();
        }

        ChatRoom chatRoom = new ChatRoom(new String[]{user1, user2}, "hihi", LocalDateTime.now().toString());
        ChatRoom chatRoomDto = mongoTemplate.insert(chatRoom, "chat_room");

        createQueue(new ChatRoomDto(chatRoomDto));

        doCreateRoomChat(chatRoom, user1);
        doCreateRoomChat(chatRoom, user2);

        return chatRoom.getId();
    }


    public void doCreateRoomChat(ChatRoom chatRoom, String userId) throws IOException {

        ChatRoomDetail chat = ChatRoomDetail.builder()
                .roomId(chatRoom.getId())
                .toUserId(userId)
                .fromUserId("admin")
                .msg("채팅방이 생성되었습니다.")
                .created_dt(LocalDateTime.now().toString())
                .readCnt(0)
                .build();

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

        StringBuilder sb = new StringBuilder()
                .append("room.")
                .append(chatRoomDto.getRoomId())
                .append(".")
                .append(userId);

        Queue queue = QueueBuilder.durable(queueName + ".queue." + chatRoomDto.getRoomId() + "." + userId).build();
        amqpAdmin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(new TopicExchange(queueName + ".exchange")).with(sb.toString());
        amqpAdmin.declareBinding(binding);
    }


    public ChatRoomDetail convertChat(Message message) {
        String str = new String(message.getBody());

        ChatRoomDetail chat = gson.fromJson(str, ChatRoomDetail.class);

        chat.setCreated_dt(LocalDateTime.now().toString());
        chat.setReadCnt(1);
        log.info("{}", chat);
        return chat;
    }


    public String saveChat(ChatRoomDetail chat) {
        ChatRoomDetail chatRoomDetail = mongoTemplate.insert(chat);

        //마지막 채팅 저장
        saveLastChat(chat);

        return chatRoomDetail.getRoomId();
    }

    public void doChat(ChatRoomDetail chat) throws IOException {
        StringBuilder sb = new StringBuilder()
                .append("room.")
                .append(chat.getRoomId())
                .append(".")
                .append(chat.getToUserId());

        Message msg = MessageBuilder.withBody(gson.toJson(chat).getBytes()).build();

        // 보낸 유저의 접속 정보 변경
        ChatRoom chatRoom = mongoTemplate.findOne(Query.query(Criteria.where("id").is(chat.getRoomId())), ChatRoom.class);
        System.out.println(chatRoom.getChatUsers().get(chat.getFromUserId()).getNowIn());
        if (!chatRoom.getChatUsers().get(chat.getFromUserId()).getNowIn()) {
            chatRoom.getChatUsers().get(chat.getFromUserId()).setNowIn(true);
        }
        if (!chatRoom.getChatUsers().get(chat.getToUserId()).getNowIn()){
            chatRoom.getChatUsers().get(chat.getFromUserId()).setNowIn(true);
        }
        mongoTemplate.save(chatRoom);

        rabbitTemplate.send("chat.exchange", sb.toString(), msg);
        PagedResponse<ChatRoomListDto> fromUserList = getChatRoomList(chat.getFromUserId());
        PagedResponse<ChatRoomListDto> toUserList = getChatRoomList(chat.getToUserId());

        rabbitTemplate.convertAndSend("user.exchange", "user." + chat.getToUserId(), gson.toJson(toUserList));

        rabbitTemplate.convertAndSend("user.exchange", "user." + chat.getFromUserId(), gson.toJson(fromUserList));

        /* FCM 알림 - 안드로이드 FCM 연결 시, 주석 풀 것. */
//        Member member = mongoTemplate.findOne(Query.query(Criteria.where("id").is(chat.getFromUserId())), Member.class);
//        UserAppToken userAppToken = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(chat.getToUserId())), UserAppToken.class);
//        firebaseCloudMessageService.sendMessageTo(userAppToken.getAppToken(), member.getName(), chat.getMsg());


    }


    ///////////////////////////////////////

    public PagedResponse<ChatRoomDetail> getChatHistory(String chatRoomId, int offset, int size, String userId) {

        Pageable pageable = PageRequest.of(offset - 1, size, Sort.by(Sort.Direction.ASC, "created_dt"));

        ChatRoom chatRoom = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("id").is(chatRoomId)), ChatRoom.class)).orElseThrow(
                () -> new ResourceNotFoundException("ChatRoom", "chatRoomId", chatRoomId)
        );

        String leaveTime = chatRoom.getChatUsers().get(userId).getNowIn() ? chatRoom.getDate() : chatRoom.getLeaveTime();


        Query query = new Query(Criteria.where("roomId").is(chatRoomId)
                .and("created_dt").gte(leaveTime)).with(pageable);

        List<ChatRoomDetail> filteredMetaData =  Optional.ofNullable(mongoTemplate.find(query, ChatRoomDetail.class)).orElseThrow(
                () -> new ResourceNotFoundException("채팅 내역이 없습니다")
        );

        Page<ChatRoomDetail> metaDataPage = PageableExecutionUtils.getPage(filteredMetaData, pageable, () -> mongoTemplate.count(query.skip(-1).limit(-1), ChatRoomDetail.class)
        );

        return new PagedResponse<>(metaDataPage.getContent(), metaDataPage.getTotalPages());

    }


    public PagedResponse<ChatRoomListDto> getChatRoomList(String userId) {
        List<ChatRoomListDto> chatRoomListDtoList = new ArrayList<>();

        List<LastChat> lastChatList = getLastChatList(userId);
        int size = lastChatList.size();

        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);

        for (int i = size - 1; i >= 0; i--) {

            LastChat lastChat = lastChatList.get(i);
            String otherUserId = lastChat.getFromUserId().equals(userId) ? lastChat.getToUserId() : lastChat.getFromUserId();
            
            Optional<Member> memberOptional = getMemberById(otherUserId);
            if (memberOptional.isPresent()) {
                Member member = memberOptional.get();
                ChatRoomListDto chatRoomListDto = new ChatRoomListDto(lastChat);
                chatRoomListDto.setProfile(member.getImageUrl());
                chatRoomListDto.setName(member.getName());

                QueueInformation queueInformation = getQueueInfo(rabbitAdmin, lastChat.getRoomId(), userId);
                if (queueInformation != null) {
                    log.info("queueInfo cnt : {}", queueInformation.getMessageCount());
                    chatRoomListDto.setNoReadCnt(queueInformation.getMessageCount());
                } else {
                    System.out.println("queueInformation is null");
                }

                chatRoomListDtoList.add(chatRoomListDto);
            }
        }

        return new PagedResponse<>(chatRoomListDtoList, 1);
    }


    private Optional<Member> getMemberById(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        return Optional.ofNullable(mongoTemplate.findOne(query, Member.class));
    }

    private QueueInformation getQueueInfo(RabbitAdmin rabbitAdmin, String roomId, String userId) {
        String queueName = String.format("chat.queue.%s.%s", roomId, userId);
        return rabbitAdmin.getQueueInfo(queueName);
    }


    public void saveLastChat(ChatRoomDetail chat) {

        saveLastChatDetail(chat, chat.getFromUserId());
        saveLastChatDetail(chat, chat.getToUserId());
    }

    ;

    public void saveLastChatDetail(ChatRoomDetail chat, String userId) {

        mongoTemplate.remove(Query.query(Criteria.where("roomId").is(chat.getRoomId())), LastChat.class);

        LastChat lastChatDto = new LastChat(chat);
        lastChatDto.setUserId(userId);
        mongoTemplate.insert(lastChatDto, "last_chat");
    }

    public List<LastChat> getLastChatList(String userId) {
        return mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)), LastChat.class);
    }


    /*
        1. 남아 있는 사람이 2명일 때, 유저 목록 삭제 x
        2. leaveUser에 userId 저장하고, leaveTime 저장
        3. 남아 있는 사람이 1명일 때, chat_room + chat_room_detail + last_chat도 완전삭제
    */
    public String deleteRoom(String roomId, String userId) throws IOException {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(roomId));
        ChatRoom chatRoom = mongoTemplate.findOne(Query.query(Criteria.where("id").is(roomId)), ChatRoom.class);

//        /* chat.queue, read.queue 삭제 */
//        deleteQueue("chat.queue", chatRoom.getId(), userId);
//        deleteQueue("read.queue", chatRoom.getId(), userId);

        String toUserId = Arrays.stream(chatRoom.getUsers()).filter(a -> !a.equals(userId))
                .toArray(String[]::new)[0];

        if (!chatRoom.getChatUsers().get(toUserId).getNowIn()) { // 이전에 떠난 사용자와 현재 떠나려는 사용자의 아이디가 일치하지 않을 경우, 채팅방 폭파
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

        chatRoom.getChatUsers().get(userId).setNowIn(false);
        chatRoom.getChatUsers().get(userId).setLeaveTime(LocalDateTime.now().toString());
        mongoTemplate.save(chatRoom);

        // '나'의 라스트 챗 삭제 => 채팅 목록에서 사라지도록
        mongoTemplate.remove(Query.query(Criteria.where("roomId").is(roomId)
                .and("userId").is(userId)), LastChat.class);

//        if (chatRoom == null) {
//            throw new ResourceNotFoundException("없는 채팅방 번호입니다");
//        }

//        String toUserId = Arrays.stream(chatRoom.getUsers()).filter(a -> !a.equals(userId))
//                .toArray(String[]::new)[0];
//
//        System.out.println("toUserId :: " + toUserId);
//
//        ChatRoomDetail chat = ChatRoomDetail.builder()
//                .roomId(chatRoom.getId())
//                .toUserId(toUserId)
//                .fromUserId("admin")
//                .msg("상대방이 나갔습니다.")
//                .created_dt(LocalDateTime.now().toString())
//                .readCnt(0)
//                .build();
//
//        doChat(chat);
//
//        chatRoom.setUsers(new String[]{toUserId});
//        mongoTemplate.save(chatRoom);

        return roomId;
    }

    private void deleteQueue(String queueName, String roomId, String userId) {
        amqpAdmin.deleteQueue(queueName + "." + roomId + "." + userId);
    }

    public PagedResponse<UserInfo> getUserInfoByRoom(String roomId) {

        ChatRoom chatRoom = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("id").is(roomId)), ChatRoom.class))
                .orElseThrow(() -> new ResourceNotFoundException("없는 채팅방 입니다"));

        String[] userIds = chatRoom.getUsers();

        List<Member> members = mongoTemplate.find(Query.query(Criteria.where("id").in(userIds)), Member.class);

        List<UserInfo> userInfos = members.stream()
                .map(member -> UserInfo.builder()
                        .userId(member.getId())
                        .userName(member.getName())
                        .profileImg(member.getImageUrl())
                        .deleteStatus(member.getDeleteStatus())
                        .build())
                .collect(Collectors.toList());

        return new PagedResponse<>(userInfos, 1);
    }
}