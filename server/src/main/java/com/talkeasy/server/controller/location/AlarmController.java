package com.talkeasy.server.controller.location;
import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.service.location.LocationAlarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"alarm 관련 API"})
@Controller
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
public class AlarmController {
    private final LocationAlarmService alarmService;
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
//    @GetMapping("/rooms")
//    @ResponseBody
//    public List<ChatRoom> room() {
//        return alarmService.findAllRoom();
//    }
    // 채팅방 생성
    @ApiOperation(value = "SOS 요청 눌렀을 때 채팅방 정보 전달", notes = "name을 보내주면 해당하는 roomId와 roomName 리턴\n'sub/{roomId}를 구독하고 저기로 메시지를 보내주면 된다.")
    @PostMapping("/sos")
    @ResponseBody
    public ResponseEntity<CommonResponse<Object>> createRoom(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, alarmService.createRoom(name)));

    }


    @ApiOperation(value = "상황 끝났을 때 채팅방 삭제", notes = "채팅방 Id를 보내주면 삭제된 roomId와 roomName 리턴")
    @PostMapping("/end")
    @ResponseBody
    public ResponseEntity<CommonResponse<Object>> deleteRoom(@RequestParam String roomId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, alarmService.deleteRoom(roomId)));
    }

    // 특정 채팅방 조회
//    @GetMapping("/room/{roomId}")
//    @ResponseBody
//    public ChatRoom roomInfo(@PathVariable String roomId) {
//        return alarmService.findById(roomId);
//    }

//    @MessageMapping("/pub")
//    public void enter(AlarmDto message) {
//        messagingTemplate.convertAndSend("/sub/"+message.getRoomId(),message);
//    }

}
