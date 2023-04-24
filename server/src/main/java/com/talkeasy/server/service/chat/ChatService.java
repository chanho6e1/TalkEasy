package com.talkeasy.server.service.chat;

import com.google.gson.Gson;
import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.domain.chat.ChatRoom;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.dto.*;
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


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final MongoTemplate mongoTemplate;
//    private final KafkaTemplate<String, MessageDto> kafkaTemplate;
//    private final KafkaAdmin kafkaAdmin;
//    private final KafkaListenerEndpointRegistry registry;

    private final RabbitTemplate rabbitTemplate;
    private final AmqpAdmin amqpAdmin;

    public String createRoom(String user1, String user2){

        ChatRoom chatRoom = new ChatRoom(new String[]{user1, user2}, "hihi", LocalDateTime.now().toString());
        ChatRoom chatRoomDto = mongoTemplate.insert(chatRoom);

        createQueue(new ChatRoomDto(chatRoomDto));

        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        QueueInformation queueInformation = null;

        StringBuilder queueName = new StringBuilder();
        queueName.append("chat.queue.").append(chatRoomDto.getId()).append(".").append(user1);
        queueInformation = rabbitAdmin.getQueueInfo(queueName.toString());

        return "done";
    }

    public void createQueue(ChatRoomDto chatRoomDto) {
        StringBuilder sb = new StringBuilder();
        sb.append("room.").append(chatRoomDto.getRoomId()).append(".").append(chatRoomDto.getSendId());

        Queue queue = QueueBuilder.durable("chat.queue." + chatRoomDto.getRoomId()+"."+chatRoomDto.getSendId()).build();
        amqpAdmin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue)
                .to(new TopicExchange("chat.exchange"))
                .with(sb.toString());
        amqpAdmin.declareBinding(binding);


        sb = new StringBuilder();
        sb.append("room.").append(chatRoomDto.getRoomId()).append(".").append(chatRoomDto.getReceiveId());

        queue = QueueBuilder.durable("chat.queue." + chatRoomDto.getRoomId()+"."+chatRoomDto.getReceiveId()).build();
        amqpAdmin.declareQueue(queue);
        binding = BindingBuilder.bind(queue)
                .to(new TopicExchange("chat.exchange"))
                .with(sb.toString());
        amqpAdmin.declareBinding(binding);
    }

    public ChatRoomDetail convertChat(Message message) {
        String str = new String(message.getBody());
        ChatRoomDetail chat = new Gson().fromJson(str,ChatRoomDetail.class);
        chat.setCreated_dt(LocalDateTime.now().toString());
        log.info("{}", chat);
        return chat;
    }


//    public Object sendMessage(MessageDto messageDto) {
//
//        log.info("send Message : " + messageDto.getMsg());
//
//        ChatRoomDetail chatRoom = new ChatRoomDetail(messageDto);
//        ChatRoomDetail newChat = mongoTemplate.insert(chatRoom);
//        messageDto.setMsgId(newChat.getId());
//
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("room.").append(messageDto.getRoomId()).append(".").append(messageDto.getReceiver());
////        if(messageDto.getRoomId() == -1 || messageDto.getSender() ==-1 || messageDto.getReceiver()==-1) return;
//
//        rabbitTemplate.send("chat.exchange", sb.toString(), messageDto.getMsg());
//        List<ChatRoomListDto> senderList = this.getChatRoomList(messageDto.getSender());
//        List<ChatRoomListDto> receiverList = this.getChatRoomList(messageDto.getReceiver());
//        Gson gson = new Gson();
//
//        rabbitTemplate.convertAndSend("user.exchange","user."+messageDto.getSender(),gson.toJson(senderList));
//        rabbitTemplate.convertAndSend("user.exchange","user."+messageDto.getReceiver(),gson.toJson(receiverList));
//
//
//        return newChat.getId();
//    }

//    public List<ChatRoomResponseDto> getChatRoom(Long userId) {
//
//        Query query = Query.query(Criteria.where("users").elemMatch(Criteria.where("$eq").is(userId)));
//        List<ChatRoom> chatRooms = mongoTemplate.find(query, ChatRoom.class);
//
//        //ChatRoomResponseDto notReadCnt 추가
//        return  chatRooms.stream()
//                .map(room -> {
//                    long notReadCnt = mongoTemplate.count(
//                            Query.query(
//                                    Criteria.where("roomId").is(room.getId())
//                                            .and("sender").ne(userId)
//                                            .and("isRead").is(false)
//                            ),
//                            ChatRoomDetail.class
//                    );
//                    return ChatRoomResponseDto.of(room, notReadCnt);
//                })
//                .collect(Collectors.toList());
//    }

    public PagedResponse<ChatRoomDetail> getChatHistory(String chatRoomId, int offset, int size) {

        Pageable pageable = PageRequest.of(offset-1, size, Sort.by(Sort.Direction.ASC, "created_dt"));
        Query query = new Query(Criteria.where("roomId").is(chatRoomId)).with(pageable);

        List<ChatRoomDetail> filteredMetaData = mongoTemplate.find(query, ChatRoomDetail.class);

        Page<ChatRoomDetail> metaDataPage = PageableExecutionUtils.getPage(
                filteredMetaData,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1),ChatRoomDetail.class)
                // query.skip(-1).limit(-1)의 이유는 현재 쿼리가 페이징 하려고 하는 offset 까지만 보기에 이를 맨 처음부터 끝까지로 set 해줘 정확한 도큐먼트 개수를 구한다.
        );

        return new PagedResponse<>(metaDataPage.getContent(), metaDataPage.getNumber()+1, metaDataPage.getSize(), metaDataPage.getTotalElements(),
                metaDataPage.getTotalPages(), metaDataPage.isLast());

    }


    public void saveChat(ChatRoomDetail chat) {

        mongoTemplate.insert(chat);
    }

    public void doChat(ChatRoomDetail chat, Message message) {
        StringBuilder sb = new StringBuilder();
        sb.append("room.").append(chat.getRoomId()).append(".").append(chat.getToUserId());
//        if(chat.getRoomId().equals("-1") || chat.getToUserId().equals("-1") || chat.getFromUserId().equals("-1) return;
        rabbitTemplate.send("chat.exchange",sb.toString(),message);
        List<ChatRoomResponseDto> fromUserList = this.getChatRoomList(chat.getFromUserId());
        List<ChatRoomResponseDto> toUserList = this.getChatRoomList(chat.getToUserId());

        Gson gson = new Gson();

        rabbitTemplate.convertAndSend("user.exchange","user."+chat.getFromUserId(),gson.toJson(fromUserList));
        rabbitTemplate.convertAndSend("user.exchange","user."+chat.getToUserId(),gson.toJson(toUserList));
    }

    public List<ChatRoomResponseDto> getChatRoomList(String userId) {
        List<ChatRoomResponseDto> list = new ArrayList<>();
        System.out.println(userId);
//        List<LastChatDto> lastChatDtos = chatDao.getLastChatList(userId);
//        int size = lastChatDtos.size();
//        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
//        QueueInformation queueInformation = null;
//        for(int i = size - 1; i >= 0; i--) {
//            LastChatDto lastChatDto = lastChatDtos.get(i);
//            ChatRoomListDto dto = new ChatRoomListDto();
//            int otherUserId = lastChatDto.getFromUserId();
//            if(userId == otherUserId) {
//                otherUserId = lastChatDto.getToUserId();
//            }
//            UserDto userDto = userService.getUserById(otherUserId);
//            dto.setRoomId(lastChatDto.getRoomId());
//            dto.setUserId(otherUserId);
//            dto.setContent(lastChatDto.getContent());
//            dto.setProfile(userDto.getProfile());
//            dto.setNickname(userDto.getNickname());
//            dto.setDate(lastChatDto.getDate());
//            StringBuilder queueName = new StringBuilder();
//            queueName.append("chat.queue.").append(lastChatDto.getRoomId()).append(".").append(userId);
//            queueInformation = rabbitAdmin.getQueueInfo(queueName.toString());
//            dto.setNoReadCnt(queueInformation.getMessageCount());
//            list.add(dto);
//        }

        return list;
    }


    public ChatRoomDto makeRoom(MakeChatRoomDto makeChatRoomDto) {

        ChatRoom chatRoom = new ChatRoom(new String[]{"user1", "user2"}, "hihi", LocalDateTime.now().toString());
        ChatRoomDto chatRoomDto = new ChatRoomDto(mongoTemplate.insert(chatRoom));

        return chatRoomDto;
    }

    public String deleteRoom(String roomId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("roomId").is(roomId));
        mongoTemplate.remove(query, "test");

        return "remove";
    }

    public Object getChatRoom(Long userId) {
        return null;
    }
}