package com.talkeasy.server.controller.chat;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.dto.MessageDto;
import com.talkeasy.server.dto.ReadMessageDto;
import com.talkeasy.server.service.chat.ChatService;
import com.talkeasy.server.service.chat.TTSService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
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
    private final ChatService chatService;
    private final TTSService ttsService;

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

//        template.convertAndSend("amq.topic", "room." + chatRoomId, chatDto); //topic

    }

    @MessageMapping("/chat.read.{chatRoomId}")
    public void read(ReadMessageDto readMessageDto, @DestinationVariable String chatRoomId) {

        System.out.println("chatRoomId " + readMessageDto.getMsgId());
        ChatRoomDetail chat = mongoTemplate.findById(readMessageDto.getMsgId(), ChatRoomDetail.class);

        if (!readMessageDto.getUserId().equals(chat.getSender())) {
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
//        messagingTemplate.convertAndSend("/queue/room." + chatDto.getRoomId(), chatDto);
//        messagingTemplate.convertAndSend("/topic/room." + chatDto.getRoomId(), chatDto);

//        System.out.println(session);
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
//        String sessionId = headerAccessor.getSessionId();
//        System.out.println(sessionId);

        log.info("chatDto.getMessage() = {}", chatDto.getMsg());
    }


    ///////////////////-------------->


    /////////////////<----------
    @GetMapping("api/chat/chat-history/{chatRoomId}")
    public ResponseEntity<CommonResponse> getChatHistory(@PathVariable String chatRoomId,
                                                         @RequestParam(required = false, defaultValue = "1") int offset,
                                                         @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                "채팅방 내부 메시지 조회", chatService.getChatHistory(chatRoomId, offset, size)));
    }

    @PostMapping("/api/chat/room")
    @ApiOperation(value = "채팅방 생성", notes = "파라미터로 sendId, receiveId, title(채팅방제목)을 주면 채팅방 아이디를 반환")
    public ResponseEntity<CommonResponse> createChatRoom(@RequestParam Long sendId, @RequestParam Long receiveId, @RequestParam String title) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                "채팅방 생성 성공", chatService.createChatRoom(sendId, receiveId, title)));
    }

    @GetMapping("/api/chat/my")
    @ApiOperation(value = "채팅방 조회", notes = "내가 속한 채팅방 리스트를 반환")
    public ResponseEntity<CommonResponse> getChatRoom(@RequestParam Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                "채팅방 조회 성공", chatService.getChatRoom(userId)));
    }

    @GetMapping("/api/chat/tts")
    @ApiOperation(value ="tts", notes = "text를 주면 음성 파일로 반환")
    public ResponseEntity<CommonResponse> getTTS(@RequestParam String text) throws IOException, UnsupportedAudioFileException {
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                "tts 조회 성공", ttsService.getTTS(text)));
    }

    /////////////////---------->

}