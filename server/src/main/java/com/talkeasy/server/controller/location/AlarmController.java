package com.talkeasy.server.controller.location;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.dto.user.MemberDetailResponse;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@Api(tags = {"alarm 관련 API"})
@Controller
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class AlarmController {
    private final SimpMessageSendingOperations messagingTemplate;
    @ApiOperation(value = "test", notes = "test")
    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse> stompAlarm(@PathVariable("userId") Long userId) {
        System.out.println("/sub/"+userId);
        messagingTemplate.convertAndSend("/sub/" + userId, "alarm socket connection completed.");
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(HttpStatus.OK, ""));

    }

//    @MessageMapping("")
//    public void message() {
//        System.out.println("메시지!!!!!!");
//        messagingTemplate.convertAndSend("", "alarm socket connection completed.");
//    }

    @RequestMapping("/greetings")
    public void greet(String greetingMessage) {
        messagingTemplate.convertAndSend("/sub", greetingMessage);
    }
}
