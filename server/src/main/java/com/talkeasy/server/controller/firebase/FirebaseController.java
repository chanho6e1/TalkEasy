package com.talkeasy.server.controller.firebase;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.dto.firebase.RequestFcmDto;
import com.talkeasy.server.service.firebase.FirebaseCloudMessageService;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fcm")
@Api(tags = {"FCM 컨트롤러"})
public class FirebaseController {
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @ApiIgnore
    @PostMapping("/test")
    @ApiOperation(value = "백엔드 테스트용", notes = "(테스트용)")
    public ResponseEntity pushMessageTest(@RequestParam String token,
                                      @RequestParam String title,
                                      @RequestParam String body) throws IOException {

        firebaseCloudMessageService.sendMessageTo(token, title, body);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, "Success"));
    }

    @PostMapping
    @ApiOperation(value = "SOS 요청 FCM 전송", notes = "FCM을 받을 유저 ID, 제목, 내용 지정")
    public ResponseEntity pushMessage(@RequestBody RequestFcmDto requestFcmDto) throws IOException {

        firebaseCloudMessageService.sendFcm(requestFcmDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, "Success"));
    }


    @PutMapping("/app-token")
    @ApiOperation(value = "유저 앱 토큰 저장", notes = "유저 로그인 시, 기기 앱 토큰 저장 필요")
    public ResponseEntity<?> saveAppToken(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User,
                                          @RequestParam String appToken) {

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, firebaseCloudMessageService.saveAppToken(oAuth2User.getId(), appToken)));
    }
}

