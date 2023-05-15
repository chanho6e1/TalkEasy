package com.talkeasy.server.controller.location;
import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.dto.location.ChatRoom;
import com.talkeasy.server.service.location.LocationAlarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"location sos alarm 관련 API"})
@Controller
@RequestMapping("/api/sos")
@RequiredArgsConstructor
public class AlarmController {
    private final LocationAlarmService alarmService;
    private final SimpMessageSendingOperations messagingTemplate;

    // 채팅방 생성
    @ApiOperation(value = "채팅방 생성하기", notes = "피보호자 아이디를 보내주면 'sub/{피보호자ID}' (리턴되는 데이터 값)를 구독하고, 채팅방을 만들어준다. 'sub/{피보호자ID}'로 메시지를 보내주면 된다.")
    @PostMapping("/start")
    @ResponseBody
    public ResponseEntity<CommonResponse<Object>> createRoom(@RequestParam String userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, alarmService.createRoom(userId)));
    }


    @ApiOperation(value = "상황 끝났을 때 채팅방 삭제", notes = "피보호자 ID를 보내주면 삭제된 채팅방 삭제 및 삭제 메시지 전송")
    @PostMapping("/end")
    @ResponseBody
    public ResponseEntity<CommonResponse<Object>> deleteRoom(@RequestParam String userId) {

        messagingTemplate.convertAndSend("/sub/"+userId, "상황이 종료되었습니다.");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CommonResponse.of(
                HttpStatus.NO_CONTENT, alarmService.deleteRoom(userId)));

    }

    @ApiOperation(value = "채팅방 존재 여부 확인", notes = "피보호자 ID를 보내주면 채팅방이 존재하는지 확인. 존재하면 true/ 그렇지 않으면 false 리턴")
    @GetMapping("/exist")
    @ResponseBody
    public ResponseEntity<CommonResponse<Object>> isRoom(@RequestParam String userId) {

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                HttpStatus.OK, alarmService.isRoom(userId)));

    }


//    @MessageMapping("/pub")
//    public void enter(AlarmDto message) {
//        messagingTemplate.convertAndSend("/sub/"+message.getRoomId(),message);
//    }

}
