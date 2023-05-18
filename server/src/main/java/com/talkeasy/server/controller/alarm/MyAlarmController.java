package com.talkeasy.server.controller.alarm;

import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.dto.alarm.RequestSosAlarmDto;
import com.talkeasy.server.service.alarm.AlarmService;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import com.google.gson.Gson;
import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.dto.chat.MessageDto;
import com.talkeasy.server.service.chat.ChatService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
@Api(tags = {"Alarm 보관함 컨트롤러"})
public class MyAlarmController {

    private final AlarmService alarmService;
    private final Gson gson;
    private final ChatService chatService;



    @GetMapping
    @ApiOperation(value = "알림 보관함 조회", notes = "현재 날짜부터 일주일 전까지의 알람을 조회한다.")
    public ResponseEntity<?> getAlarms(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User) {

//        Member member = Member.builder().id("645307321511deecd5c5441a").role(0).build();
//        return ResponseEntity.status(HttpStatus.OK).body(alarmService.getAlarms(member));
        return ResponseEntity.status(HttpStatus.OK).body(alarmService.getAlarms(oAuth2User.getMember()));
    }

    @PutMapping("/{alarmId}")
    @ApiOperation(value = "알람 읽음 처리", notes = "alarmId를 주면 읽음 처리 설정")
    public ResponseEntity<?> putReadStatusByAlarmId(@PathVariable String alarmId,
                                                    @ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User) {

//        Member member = Member.builder().id("64475bb2970b4a6441e96c50").role(0).build();
//        return ResponseEntity.status(HttpStatus.OK).body(alarmService.putReadStatusByAlarmId(alarmId, member));
        return ResponseEntity.status(HttpStatus.OK).body(alarmService.putReadStatusByAlarmId(alarmId, oAuth2User.getMember()));
    }

    @PostMapping
    @ApiOperation(value = "피보호자의 SOS 요청 저장", notes = "피보호자가 sos 버튼을 누를때마다 알림 저장한다.")
    public ResponseEntity<CommonResponse> postAlarmBySOS(@RequestBody RequestSosAlarmDto requestSosAlarmDto,
                                                            @ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User) throws IOException {

//        Member member = Member.builder().id("645307321511deecd5c5441a").role(0).build();
//        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
//                HttpStatus.OK, alarmService.postAlarmBySOS(requestSosAlarmDto, member)));
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                HttpStatus.OK, alarmService.postAlarmBySOS(requestSosAlarmDto, oAuth2User.getMember())));
    }


    @ApiIgnore
    @PostMapping("/test")
    @ApiOperation(value = "알람 디비 저장(테스트용)", notes = "테스트용")
    public ResponseEntity<CommonResponse> receiveMessage(@RequestBody MessageDto messageDto) {
        Message msg = MessageBuilder.withBody(gson.toJson(messageDto).getBytes()).build();

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                HttpStatus.OK, chatService.convertChat(msg)));
    }

}
