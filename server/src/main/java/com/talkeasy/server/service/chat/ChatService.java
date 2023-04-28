package com.talkeasy.server.service.chat;

import com.google.gson.Gson;
import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.domain.Member;
import com.talkeasy.server.domain.chat.ChatRoom;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.domain.chat.LastChat;
import com.talkeasy.server.dto.chat.*;
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


import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final MongoTemplate mongoTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final AmqpAdmin amqpAdmin;

    public String createRoom(String user1, String user2) {

        ChatRoom chatRoom = new ChatRoom(new String[]{user1, user2}, "hihi", LocalDateTime.now().toString());
        ChatRoom chatRoomDto = mongoTemplate.insert(chatRoom);

        createQueue(new ChatRoomDto(chatRoomDto));

        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        QueueInformation queueInformation = null;

        StringBuilder queueName = new StringBuilder();
        queueName.append("chat.queue.").append(chatRoomDto.getId()).append(".").append(user1);
        queueInformation = rabbitAdmin.getQueueInfo(queueName.toString());

        return chatRoom.getId();
    }

    public void createQueue(ChatRoomDto chatRoomDto) {

        /* chat.queue */

        StringBuilder sb = new StringBuilder();
        sb.append("room.").append(chatRoomDto.getRoomId()).append(".").append(chatRoomDto.getFromUserId());

        Queue queue = QueueBuilder.durable("chat.queue." + chatRoomDto.getRoomId() + "." + chatRoomDto.getFromUserId()).build();
        amqpAdmin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(new TopicExchange("chat.exchange")).with(sb.toString());
        amqpAdmin.declareBinding(binding);


        sb = new StringBuilder();
        sb.append("room.").append(chatRoomDto.getRoomId()).append(".").append(chatRoomDto.getToUserId());

        queue = QueueBuilder.durable("chat.queue." + chatRoomDto.getRoomId() + "." + chatRoomDto.getToUserId()).build();
        amqpAdmin.declareQueue(queue);
        binding = BindingBuilder.bind(queue).to(new TopicExchange("chat.exchange")).with(sb.toString());
        amqpAdmin.declareBinding(binding);

        /* read.queue */

        sb = new StringBuilder();
        sb.append("room.").append(chatRoomDto.getRoomId()).append(".").append(chatRoomDto.getFromUserId());

        queue = QueueBuilder.durable("read.queue."  + chatRoomDto.getRoomId() +  "." + chatRoomDto.getFromUserId()).build();
        amqpAdmin.declareQueue(queue);
        binding = BindingBuilder.bind(queue).to(new TopicExchange("read.exchange")).with(sb.toString());
        amqpAdmin.declareBinding(binding);

        sb = new StringBuilder();
        sb.append("room.").append(chatRoomDto.getRoomId()).append(".").append(chatRoomDto.getToUserId());

        queue = QueueBuilder.durable("read.queue."   + chatRoomDto.getRoomId() +  "." + chatRoomDto.getToUserId()).build();
        amqpAdmin.declareQueue(queue);
        binding = BindingBuilder.bind(queue).to(new TopicExchange("read.exchange")).with(sb.toString());
        amqpAdmin.declareBinding(binding);


    }

    public ChatRoomDetail convertChat(Message message) {
        String str = new String(message.getBody());
        ChatRoomDetail chat = new Gson().fromJson(str, ChatRoomDetail.class);
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

    public void doChat(ChatRoomDetail chat) {
        StringBuilder sb = new StringBuilder();
        sb.append("room.").append(chat.getRoomId()).append(".").append(chat.getToUserId());

        Gson gson = new Gson();

        Message msg = MessageBuilder.withBody(gson.toJson(chat).getBytes()).build();

        rabbitTemplate.send("chat.exchange", sb.toString(), msg);
        PagedResponse<ChatRoomListDto> fromUserList = getChatRoomList(chat.getFromUserId());
        PagedResponse<ChatRoomListDto> toUserList = getChatRoomList(chat.getToUserId());

        rabbitTemplate.convertAndSend("user.exchange", "user." + chat.getFromUserId(), gson.toJson(fromUserList));
        rabbitTemplate.convertAndSend("user.exchange", "user." + chat.getToUserId(), gson.toJson(toUserList));


    }


    ///////////////////////////////////////

    public PagedResponse<ChatRoomDetail> getChatHistory(String chatRoomId, int offset, int size) {

        Pageable pageable = PageRequest.of(offset - 1, size, Sort.by(Sort.Direction.ASC, "created_dt"));
        Query query = new Query(Criteria.where("roomId").is(chatRoomId)).with(pageable);

        List<ChatRoomDetail> filteredMetaData = mongoTemplate.find(query, ChatRoomDetail.class);

        Page<ChatRoomDetail> metaDataPage = PageableExecutionUtils.getPage(filteredMetaData, pageable, () -> mongoTemplate.count(query.skip(-1).limit(-1), ChatRoomDetail.class)
                // query.skip(-1).limit(-1)의 이유는 현재 쿼리가 페이징 하려고 하는 offset 까지만 보기에 이를 맨 처음부터 끝까지로 set 해줘 정확한 도큐먼트 개수를 구한다.
        );

        return new PagedResponse<>(metaDataPage.getContent(), metaDataPage.getTotalPages());

    }


    public PagedResponse<ChatRoomListDto> getChatRoomList(String userId) {
        List<ChatRoomListDto> chatRoomListDtoList = new ArrayList<>();
//        System.out.println(userId);

        List<LastChat> lastChatList = getLastChatList(userId);
        int size = lastChatList.size();

        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        int idx = 0;

        for (int i = size - 1; i >= 0; i--) {

            LastChat lastChat = lastChatList.get(i);
            String otherUserId = lastChat.getFromUserId();

            ChatRoomListDto chatRoomListDto = new ChatRoomListDto(lastChat);

            if (userId == otherUserId) { //내가 보낸 사람이라면
                otherUserId = lastChat.getToUserId();
            }

            Member member = mongoTemplate.findOne(Query.query(Criteria.where("id").is(otherUserId)), Member.class); // 수신자의 정보 가져오기

            chatRoomListDto.setProfile(member.getImageUrl()); // 수신자 프로필
            chatRoomListDto.setName(member.getName()); // 수신자 이름

            StringBuilder queueName = new StringBuilder();
            queueName.append("chat.queue.").append(lastChat.getRoomId()).append(".").append(userId); // 내꺼
            QueueInformation queueInformation = rabbitAdmin.getQueueInfo(queueName.toString());

            if (queueInformation != null) {
                log.info("queueInfo cnt : {}", queueInformation.getMessageCount());
                chatRoomListDto.setNoReadCnt(queueInformation.getMessageCount());
                System.out.println("queueInformation.getMessageCount() " + queueInformation.getMessageCount() + " " + idx++);
            } else {
                System.out.println(idx++);
                System.out.println("queueInformation is null");
            }
            chatRoomListDtoList.add(chatRoomListDto);
        }

        return new PagedResponse<>(chatRoomListDtoList, 1);

    }


    public void saveLastChat(ChatRoomDetail chat) {

        mongoTemplate.remove(Query.query(Criteria.where("roomId").is(chat.getRoomId())), LastChat.class);

        LastChat lastChatDto = new LastChat(chat);
        lastChatDto.setUserId(chat.getFromUserId());
        mongoTemplate.save(lastChatDto, "last_chat");

        lastChatDto.setUserId(chat.getToUserId());
        mongoTemplate.save(lastChatDto, "last_chat");
    }

    public List<LastChat> getLastChatList(String userId) {
        return mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)), LastChat.class);
    }


    public String deleteRoom(String roomId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(roomId));
        ChatRoom chatRoom = mongoTemplate.findOne(query, ChatRoom.class);

//        if (chatRoom == null) {
//            throw new ResourceNotFoundException("없는 채팅방 번호입니다");
//        }

        mongoTemplate.remove(query, ChatRoom.class); //채팅방 삭제
        mongoTemplate.remove(Query.query(Criteria.where("roomId").is(roomId)), ChatRoomDetail.class); //채팅 내역 삭제

        return roomId;
    }

    public PagedResponse<UserInfo> getUserInfoByRoom(String roomId) {

        ChatRoom chatRoom = mongoTemplate.findOne(Query.query(Criteria.where("id").is(roomId)), ChatRoom.class);
        String[] user = chatRoom.getUsers();

        Member member1 = mongoTemplate.findOne(Query.query(Criteria.where("id").is(user[0])), Member.class);
        Member member2 = mongoTemplate.findOne(Query.query(Criteria.where("id").is(user[1])), Member.class);

        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(UserInfo.builder().userId(member1.getId()).userName(member1.getName()).profileImg(member1.getImageUrl()).build());
        userInfos.add(UserInfo.builder().userId(member2.getId()).userName(member2.getName()).profileImg(member2.getImageUrl()).build());

        return new PagedResponse<>(userInfos, 1);
    }


}