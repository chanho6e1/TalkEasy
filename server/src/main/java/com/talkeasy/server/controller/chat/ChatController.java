package com.talkeasy.server.controller.chat;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.domain.Test;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.dto.MessageDto;
import com.talkeasy.server.dto.ReadMessageDto;
import com.talkeasy.server.service.chat.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
//@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Api(tags = {"chat 컨트롤러"})
public class ChatController {//producer

    private final ChatService chatService;
    private final KafkaTemplate<String, MessageDto> kafkaTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final MongoTemplate mongoTemplate;

    @PostMapping("/api/chat/room")
    @ApiOperation(value = "채팅방 생성", notes = "자세한 설명(어떤 값을 입력하고 어떤 값을 반환하는지)")
    public ResponseEntity<CommonResponse> createChatRoom(@RequestParam Long sendId, @RequestParam Long receiveId, @RequestParam String title) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                "채팅방 생성 성공", chatService.createChatRoom(sendId, receiveId, title)));
    }

    @PostMapping("/send")
    @ApiOperation(value = "채팅 메시지 전송", notes = "자세한 설명(어떤 값을 입력하고 어떤 값을 반환하는지)")
    public ResponseEntity<CommonResponse> sendMessage1(@RequestBody MessageDto messageDto) {
        System.out.println("messageDto"+messageDto);

        messagingTemplate.convertAndSend("/topic/"+ messageDto.getRoomId() , messageDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                "채팅 메시지 전송 성공", chatService.sendMessage(messageDto)));
    }


    @MessageMapping("/chat")
    @ApiOperation(value = "채팅 메시지 전송", notes = "자세한 설명(어떤 값을 입력하고 어떤 값을 반환하는지)")
    public void sendMessage(@RequestBody MessageDto messageDto) {

        System.out.println("채팅 메시지 전송 MessageMapping 성공");

        ChatRoomDetail chatRoom = new ChatRoomDetail(messageDto);
        ChatRoomDetail newChat = mongoTemplate.insert(chatRoom);
        messageDto.setMsgId(newChat.getId());
        System.out.println(newChat.getId());

        kafkaTemplate.send(messageDto.getRoomId(), messageDto);
//        messagingTemplate.convertAndSend("/topic/64411ac7fe7e9b41ad3cb0ae", messageDto);
    }

//    @MessageMapping()
//    @SendTo("/topic/exam-topic")
//    public  ResponseEntity<CommonResponse>  broadcastGroupMessage(@RequestBody MessageDto messageDto) {
//        System.out.println("컨트롤러 되내요??");
//        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
//                "채팅 메시지 전송 성공", messageDto));
//    }


    @MessageMapping("/read")
    public void handleReadMessageEvent(ReadMessageDto readMessageDto) {

        ChatRoomDetail chat = mongoTemplate.findById(readMessageDto.getMsgId(), ChatRoomDetail.class);

        if(!readMessageDto.getUserId().equals(chat.getSender())){
            chat.setReadStatus(true);
            //set한다고 다비에 바로 반영되지 않음 save를 통해 디비에 반영시켜야 함
            mongoTemplate.save(chat); 
        }
    }

    @GetMapping("/api/chat/my")
    @ApiOperation(value = "채팅방 조회", notes = "자세한 설명(어떤 값을 입력하고 어떤 값을 반환하는지)")
    public ResponseEntity<CommonResponse> getChatRoom(@RequestParam Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                "채팅방 조회 성공", chatService.getChatRoom(userId)));
    }


}

