package com.talkeasy.server.controller.member;

import com.talkeasy.server.authentication.JwtTokenProvider;
import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.dto.user.MemberDetailRequest;
import com.talkeasy.server.service.member.MemberService;
import com.talkeasy.server.service.member.OAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Api(tags = {"Member 관련 API"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class OAuthController {
    private final OAuthService oAuthService;

    @ApiOperation(value = "로그인 하기", notes = "access token 을 보내면 유저정보 반환")
    @PostMapping("/login")
    public ResponseEntity<CommonResponse> login(@RequestParam(value = "accessToken") String accessToken) { // 인가 코드
        log.info("========== /login/oauth accessToken : {}", accessToken);

        String token = null;
        try {
            token = oAuthService.getToken(accessToken);
        } catch (Exception e) {
            log.info("========== error : {}", e.getMessage());
        }

        return ResponseEntity.ok().body(CommonResponse.of("로그인 성공", token));
    }

    @ApiOperation(value = "회원정보 등록하기", notes = "회원정보 등록하기")
    @PostMapping("/register")
    ResponseEntity<CommonResponse> registerUser(@RequestBody MemberDetailRequest member) {
        log.info("========== /register registerUser name : {}, email : {}, imageUrl : {}", member.getName(), member.getEmail(), member.getImageUrl());

        String token = oAuthService.registerUser(member);

        return ResponseEntity.ok().body(CommonResponse.of("회원가입 성공", token));
    }

}
