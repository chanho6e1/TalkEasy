package com.talkeasy.server.controller.chat;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.dto.chat.ChatReadDto;
import com.talkeasy.server.dto.chat.MakeChatRoomDto;
import com.talkeasy.server.service.chat.ChatService;
import com.talkeasy.server.service.chat.ChatTestService;
import com.talkeasy.server.service.chat.ChatUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;


@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
@Api(tags = {"chat 컨트롤러"})
public class ChatController {//producer

    private final ChatService chatService;
    //    private final SimpMessagingTemplate messagingTemplate;
    private final MongoTemplate mongoTemplate;
    private final ChatUserService chatUserService;
    private final ChatTestService chatTestService;

    @PostMapping()
    @ApiOperation(value = "채팅방 생성", notes = "user1, user2 주면 채팅방 아이디를 반환")
    public ResponseEntity<?> createRoom(@RequestBody MakeChatRoomDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                "채팅방 생성 성공", chatService.createRoom(dto.getUser1(), dto.getUser2())));
    }

    @DeleteMapping("/{roomId}")
    @ApiOperation(value = "채팅방 삭제", notes = "PathVariable로 roomId 주면 삭제한 roomId 아이디를 반환")
    public ResponseEntity<?> deleteRoom(@PathVariable String roomId) {

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                "채팅방 삭제 성공", chatService.deleteRoom(roomId)));
    }

    @GetMapping("/{roomId}")
    @ApiOperation(value = "채팅방 참가자 정보 조회", notes = "PathVariable로 roomId 주면 채팅방 참가자 정보 반환")
    public ResponseEntity<?> getUserInfoByRoom(@PathVariable String roomId) {

        return ResponseEntity.status(HttpStatus.OK).body(
                chatService.getUserInfoByRoom(roomId));
    }


    @MessageMapping("/read")
    public void handleReadMessageEvent(ChatReadDto chatReadDto) {

        List<ChatRoomDetail> chatList = mongoTemplate.find(Query.query(Criteria.where("created_dt").lt(chatReadDto.getReadTime()).and("readCnt").gt(0).and("roomId").is(chatReadDto.getRoomId())), ChatRoomDetail.class);

        for (ChatRoomDetail chat : chatList) {
            if (!chatReadDto.getReadUserId().equals(chat.getFromUserId())) {
                if (chat.getReadCnt() > 0) {
                    chat.setReadCnt(0);
                    mongoTemplate.save(chat);

                }
            }
        }
    }

    /////////////////<----------
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


    /////////////////---------->
    ///test
    @GetMapping("/user")
    @ApiOperation(value = "회원가입시 큐생성(테스트용)", notes = "쿼리스트링으로 userId를 주면 큐를 만듬")
    public ResponseEntity<CommonResponse> createUserQueue(@RequestParam String userId) throws IOException, UnsupportedAudioFileException {
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                "큐 생성 성공", chatUserService.createUserQueue(userId)));
    }

    @GetMapping("/test/receive")
    @ApiOperation(value = "큐에서 수신(테스트용)", notes = "큐에서 수신")
    public ResponseEntity<CommonResponse> receiveMessage(String roomId, String recieveUserId) throws IOException, UnsupportedAudioFileException, TimeoutException {
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                "큐에서 수신 성공", chatTestService.receiveMessage(roomId, recieveUserId)));
    }

    @GetMapping("/test/send")
    @ApiOperation(value = "큐로 송신(테스트용)", notes = "큐로 송신")
    public ResponseEntity<CommonResponse> sendMessage(String msg) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                "큐에서 수신 성공", chatTestService.sendMessage(msg)));
    }
}

