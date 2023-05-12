package com.talkeasy.server.controller.alarm;

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

@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
@Api(tags = {"alarm 컨트롤러"})
public class MyAlarmController {

    private final AlarmService alarmService;

    @GetMapping
    @ApiOperation(value = "알림 보관함 조회", notes = "현재 날짜부터 일주일 전까지의 알람을 조회한다.")
    public ResponseEntity<?> getAlarms(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User) {

        return ResponseEntity.status(HttpStatus.OK).body(alarmService.getAlarms(oAuth2User.getMember()));
    }

    @PutMapping("/{alarmId}")
    @ApiOperation(value = "알람 읽음 처리", notes = "alarmId를 주면 읽음 처리 설정")
    public ResponseEntity<?> putReadStatusByAlarmId(@PathVariable String alarmId,
                                                    @ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User) {

        return ResponseEntity.status(HttpStatus.OK).body(alarmService.putReadStatusByAlarmId(alarmId, oAuth2User.getMember()));
    }
}
