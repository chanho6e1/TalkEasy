package com.talkeasy.server.controller.firebase;

import com.talkeasy.server.common.CommonResponse;
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

    @PostMapping
    @ApiOperation(value = "백엔드 테스트용", notes = "(테스트용)")
    public ResponseEntity pushMessage(@RequestParam String token, @RequestParam String title, @RequestParam String body) throws IOException {

        firebaseCloudMessageService.sendMessageTo(token, title, body);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/app-token")
    @ApiOperation(value = "유저 앱 토큰 저장", notes = "유저 로그인 시, 기기 앱 토큰 저장 필요")
    public ResponseEntity<?> saveAppToken(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User, @RequestParam String appToken) {

//        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
//                "유저 앱 토큰 저장 성공", firebaseCloudMessageService.saveAppToken(oAuth2User.getId(), appToken)));
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                "유저 앱 토큰 저장 성공", firebaseCloudMessageService.saveAppToken("1", appToken)));
    }
}

