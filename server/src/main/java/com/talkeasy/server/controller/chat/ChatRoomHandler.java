package com.talkeasy.server.controller.chat;

import com.google.gson.Gson;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.dto.chat.ChatRoomDto;
import com.talkeasy.server.dto.chat.MakeChatRoomDto;
import com.talkeasy.server.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChatRoomHandler {
    private final ChatService chatService;
    //서버로 들어오는 채팅값
    @RabbitListener(queues = "chat.queue")
    public void chatControl(Message message) {
        // Json : String -> Object : ChatDto

        log.info("message : {}", message);
        System.out.println("hihihihhihi");

        ChatRoomDetail chat = chatService.convertChat(message);
        if(chat.getRoomId().equals("0")) {
            return;
        }
//        //비즈니스 로직 (mongodb 저장, 채팅 보내기)
        String roomId = chatService.saveChat(chat);
        chat.setRoomId(roomId);
        chatService.doChat(chat, message);

    }

    @RabbitListener(queues = "room.queue")
    public void makeRoom(Message message) {
        log.info("message : {}", message);
        System.out.println("room");

        String str = new String(message.getBody());
        MakeChatRoomDto dto = new Gson().fromJson(str, MakeChatRoomDto.class);
        chatService.createRoom(dto.getUser1(), dto.getUser2());
    }

    @RabbitListener(queues = "chat.queue.644622d7619f462dc0aad696.1")
    public void getTest(Message message) {
        log.info("message : {}", message);
        System.out.println("getTest");

//        String str = new String(message.getBody());
//        MakeChatRoomDto dto = new Gson().fromJson(str, MakeChatRoomDto.class);
//        ChatRoomDto chatRoomDto = chatService.makeRoom(dto);
//        chatService.createQueue(chatRoomDto);
    }

}
