package com.talkeasy.server.controller.alarm;

import com.google.gson.Gson;
import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.dto.chat.MessageDto;
import com.talkeasy.server.service.chat.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
@Api(tags = {"alarm 컨트롤러"})
public class MyAlarmController {

    private final Gson gson;
    private final ChatService chatService;

    @PostMapping("/test")
    @ApiOperation(value = "알람 디비 저장(테스트용)", notes = " ")
    public ResponseEntity<CommonResponse> receiveMessage(@RequestBody MessageDto messageDto) {
        Message msg = MessageBuilder.withBody(gson.toJson(messageDto).getBytes()).build();

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                HttpStatus.OK, chatService.convertChat(msg)));
    }
}
