package com.talkeasy.server.controller.chat;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.dto.chat.ReadMessageDto;
import com.talkeasy.server.service.chat.ChatService;
import com.talkeasy.server.service.chat.TTSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;


@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Api(tags = {"chat 컨트롤러"})
public class ChatController {//producer

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final MongoTemplate mongoTemplate;
    private final TTSService ttsService;

    @PostMapping("/create")
    @ApiOperation(value = "채팅방 생성", notes = "파라미터로 sendUserId, receiveUserId 주면 채팅방 아이디를 반환")
    public ResponseEntity<?> createRoom(@RequestParam String user1, @RequestParam String user2) {

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                "채팅방 생성 성공", chatService.createRoom(user1, user2)));
    }

    @DeleteMapping("/{roomId}")
    @ApiOperation(value = "채팅방 삭제", notes = "PathVariable로 roomId 주면 삭제한 roomId 아이디를 반환")
    public ResponseEntity<?> deleteRoom(@PathVariable String roomId) {

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                "채팅방 삭제 성공", chatService.deleteRoom(roomId)));
    }


    @MessageMapping("/read")
    public void handleReadMessageEvent(ReadMessageDto readMessageDto) {

        ChatRoomDetail chat = mongoTemplate.findById(readMessageDto.getMsgId(), ChatRoomDetail.class);

        if(!readMessageDto.getUserId().equals(chat.getFromUserId())){
            chat.setReadStatus(true);
            mongoTemplate.save(chat);
        }
    }

    /////////////////<----------
    @GetMapping("/chat-history/{chatRoomId}")
    public ResponseEntity<CommonResponse> getChatHistory(@PathVariable String chatRoomId,
                                                         @RequestParam(required = false, defaultValue = "1") int offset,
                                                         @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                "채팅방 내부 메시지 조회", chatService.getChatHistory(chatRoomId, offset, size)));
    }


    @GetMapping("/my")
    @ApiOperation(value = "채팅방 조회", notes = "내가 속한 채팅방 리스트를 반환")
    public ResponseEntity<CommonResponse> getChatRoom(@RequestParam Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                "채팅방 조회 성공", chatService.getChatRoom(userId)));
    }

    @GetMapping("/tts")
    @ApiOperation(value ="text-to-speech", notes = "text를 주면 음성 파일로 반환")
    public ResponseEntity<CommonResponse> getTTS(@RequestParam String text) throws IOException, UnsupportedAudioFileException {
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                "tts 조회 성공", ttsService.getTTS(text)));
    }

    /////////////////---------->



}

