package com.talkeasy.server.controller.chat;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.dto.chat.MakeChatRoomDto;
import com.talkeasy.server.service.chat.ChatService;
import com.talkeasy.server.service.chat.ChatTestService;
import com.talkeasy.server.service.chat.ChatUserQueueService;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;


@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
@Api(tags = {"chat 컨트롤러"})
public class ChatController {//producer

    private final ChatService chatService;
//    private final SimpMessagingTemplate messagingTemplate;
    private final MongoTemplate mongoTemplate;
    private final ChatUserQueueService chatUserQueueService;
    private final ChatTestService chatTestService;

    @PostMapping()
    @ApiOperation(value = "채팅방 생성", notes = "user1, user2 주면 채팅방 아이디를 반환")
    public ResponseEntity<?> createRoom(@RequestBody MakeChatRoomDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                "채팅방 생성 성공", chatService.createRoom(dto.getUser1(), dto.getUser2())));
    }

    @DeleteMapping("/{roomId}")
    @ApiOperation(value = "채팅방 삭제(나가기)", notes = "PathVariable로 roomId 주면 삭제한 roomId 아이디를 반환, '나가기'실행한 사용자만 삭제되고 남은 유저는 그대로 유지")
    public ResponseEntity<?> deleteRoom(@PathVariable String roomId,
                                        @RequestParam String userId,
                                        @ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User) throws IOException {

//        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
//                "채팅방 삭제 성공", chatService.deleteRoom(roomId, oAuth2User.getId())));
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                "채팅방 삭제 성공", chatService.deleteRoom(roomId, userId)));
    }

    @GetMapping("/{roomId}")
    @ApiOperation(value = "채팅방 참가자 정보 조회", notes = "PathVariable로 roomId 주면 채팅방 참가자 정보 반환")
    public ResponseEntity<?> getUserInfoByRoom(@PathVariable String roomId) {

        return ResponseEntity.status(HttpStatus.OK).body(
                chatService.getUserInfoByRoom(roomId));
    }


    @GetMapping("/chat-history/{chatRoomId}")
    public ResponseEntity<PagedResponse> getChatHistory(@PathVariable String chatRoomId,
                                                        @RequestParam(required = false, defaultValue = "1") int offset,
                                                        @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(chatService.getChatHistory(chatRoomId, offset, size));
    }


    @GetMapping("/my")
    @ApiOperation(value = "채팅방 조회", notes = "내가 속한 채팅방 리스트를 반환")
    public ResponseEntity<?> getChatRoom(@RequestParam String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(chatService.getChatRoomList(userId));
    }


    /* test */
    @GetMapping("/user")
    @ApiOperation(value = "회원가입시 큐생성(테스트용)", notes = "쿼리스트링으로 userId를 주면 큐를 만듬")
    public ResponseEntity<CommonResponse> createUserQueue(@RequestParam String userId) throws IOException, UnsupportedAudioFileException {
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                "큐 생성 성공", chatUserQueueService.createUserQueue(userId)));
    }

    @GetMapping("/test/receive")
    @ApiOperation(value = "회원가입시 큐생성(테스트용)", notes = " chat/read")
    public ResponseEntity<CommonResponse> createUserQueue(@RequestParam String roomId, String recieveUserId, String queueName) throws IOException, UnsupportedAudioFileException, TimeoutException {
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                "큐 생성 성공", chatTestService.receiveMessage(roomId, recieveUserId, queueName)));
    }


}

