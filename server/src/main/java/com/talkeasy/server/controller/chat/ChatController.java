package com.talkeasy.server.controller.chat;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.dto.MessageDto;
import com.talkeasy.server.service.chat.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Api(tags = {"chat 컨트롤러"})
public class ChatController {//producer

    private final ChatService chatService;

    @PostMapping
    @ApiOperation(value = "채팅방 생성", notes = "자세한 설명(어떤 값을 입력하고 어떤 값을 반환하는지)")
    public ResponseEntity<CommonResponse> createChatRoom(@RequestParam Long sendId, @RequestParam Long receiveId, @RequestParam String title) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                "채팅방 생성 성공", chatService.createChatRoom(sendId, receiveId, title)));
    }

    @PostMapping("/send")
    @MessageMapping("/chat")
    @ApiOperation(value = "채팅 메시지 전송", notes = "자세한 설명(어떤 값을 입력하고 어떤 값을 반환하는지)")
    public ResponseEntity<CommonResponse> sendMessage(@RequestBody MessageDto messageDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                "채팅 메시지 전송 성공", chatService.sendMessage(messageDto)));
    }

    @MessageMapping()
    @SendTo("/topic/exam-topic")
    public  ResponseEntity<CommonResponse>  broadcastGroupMessage(@RequestBody MessageDto messageDto) {
        System.out.println("컨트롤러 되내요??");
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                "채팅 메시지 전송 성공", messageDto));
    }

}

