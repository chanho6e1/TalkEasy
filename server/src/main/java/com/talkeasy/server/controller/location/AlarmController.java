package com.talkeasy.server.controller.location;
import com.talkeasy.server.dto.location.AlarmDto;
import com.talkeasy.server.dto.location.ChatRoom;
import com.talkeasy.server.service.location.AlarmService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api(tags = {"alarm 관련 API"})
@Controller
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;
    private final SimpMessageSendingOperations messagingTemplate;
//    @ApiOperation(value = "test", notes = "test")
//    @GetMapping("/{userId}")
//    public ResponseEntity<CommonResponse> stompAlarm(@PathVariable("userId") Long userId) {
//        System.out.println("/sub/"+userId);
//        messagingTemplate.convertAndSend("/sub/" + userId, "alarm socket connection completed.");
//        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(HttpStatus.OK, ""));
//
//    }

//    @MessageMapping("")
//    public void message() {
//        System.out.println("메시지!!!!!!");
//        messagingTemplate.convertAndSend("", "alarm socket connection completed.");
//    }

//    @RequestMapping("/greetings")
//    public void greet(String greetingMessage) {
//        messagingTemplate.convertAndSend("/sub", greetingMessage);
//    }

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return alarmService.findAllRoom();
    }
    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return alarmService.createRoom(name);
    }


    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return alarmService.findById(roomId);
    }

    @MessageMapping("/pub")
    public void enter(AlarmDto message) {
        messagingTemplate.convertAndSend("/sub/"+message.getRoomId(),message);
    }

}
