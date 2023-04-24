package com.talkeasy.server.controller.chat;

import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.dto.chat.MessageDto;
import com.talkeasy.server.dto.chat.ReadMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequiredArgsConstructor
@Slf4j
public class StompRabbitController {

    private final RabbitTemplate template;

    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final static String CHAT_QUEUE_NAME = "chat.queue";
    private final MongoTemplate mongoTemplate;
    private final SimpMessagingTemplate messagingTemplate;


    //////////////////<-----------

    @MessageMapping("chat.enter.{chatRoomId}")
    public void enter(MessageDto chatDto, @DestinationVariable String chatRoomId) {
        chatDto.setMsg("입장하셨습니다.");
        chatDto.setCreated_dt(String.valueOf(LocalDateTime.now()));

        // exchange
        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chatDto);
//        template.convertAndSend("amq.topic", "room." + chatRoomId, chatDto); //topic

    }


    @MessageMapping("chat.message.{chatRoomId}")
    public void send(MessageDto chatDto, @DestinationVariable String chatRoomId) {

        System.out.println("chatRoomId " + chatRoomId);
        chatDto.setCreated_dt(String.valueOf(LocalDateTime.now()));

        ChatRoomDetail chatRoom = new ChatRoomDetail(chatDto);
        ChatRoomDetail newChat = mongoTemplate.insert(chatRoom);
        chatDto.setMsgId(newChat.getId());
        System.out.println("setMsgId " + newChat.getId());

        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chatDto);

        ////////////////////

    }

    @MessageMapping("/chat.read.{chatRoomId}")
    public void read(ReadMessageDto readMessageDto, @DestinationVariable String chatRoomId) {

        System.out.println("chatRoomId " + readMessageDto.getMsgId());
        ChatRoomDetail chat = mongoTemplate.findById(readMessageDto.getMsgId(), ChatRoomDetail.class);

        if (!readMessageDto.getUserId().equals(chat.getFromUserId())) {
            chat.setReadStatus(true);
            mongoTemplate.save(chat);
        }
    }

    //작동 안됨
    @MessageMapping(value = "/chat.leave.{roomId}")
    @SendTo("/exchange/chat.exchange/room.{roomId}")
    public void chatLeave(@DestinationVariable String roomId, MessageDto message) {
        // 생략
        System.out.println("out");
        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + message.getRoomId(), message);

    }

    // receiver()는 단순히 큐에 들어온 메세지를 소비만 한다.
    //, concurrency = "3" : 컨슈머가 3개
    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receive(MessageDto chatDto) {

        messagingTemplate.convertAndSend("/exchange/chat.exchange/room." + chatDto.getRoomId(), chatDto);
        log.info("chatDto.getMessage() = {}", chatDto.getMsg());
    }





}