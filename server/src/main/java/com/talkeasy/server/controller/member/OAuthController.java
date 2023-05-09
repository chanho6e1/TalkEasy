package com.talkeasy.server.controller.member;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.dto.user.LoginResponse;
import com.talkeasy.server.dto.user.MemberDetailRequest;
import com.talkeasy.server.service.member.OAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {"Oauth 로그인 관련 API"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
@Slf4j
public class OAuthController {
    private final OAuthService oAuthService;

    @ApiOperation(value = "로그인 하기", notes = "access token 을 보내면 유저정보 반환")
    @PostMapping("/login")
    public ResponseEntity<CommonResponse> login(@ApiParam("") @RequestParam(value = "accessToken") String accessToken, @RequestParam(value = "role") int role) { // 인가 코드
        log.info("========== /login/oauth accessToken : {}", accessToken);


        LoginResponse response = oAuthService.getToken(accessToken, role);

        if (response.getToken() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                    HttpStatus.CREATED, ""));
        }

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                HttpStatus.OK, response));
    }

    @ApiOperation(value = "회원정보 등록하기", notes = "return : jwt 토큰 , role(0 : 보호자, 1 : 피보호자), gender(0 : 남자, 1 : 여자),  * email : 입력제외")
    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<CommonResponse> registerUser(@ApiParam(value = "사용자 등록 이미지") @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile,
                                                @RequestPart(name = "value") MemberDetailRequest member) {
        log.info("========== /register registerUser name : {}, email : {}, imageUrl : {}", member.getName(), member.getEmail(), member.getImageUrl());

        String token = oAuthService.registerUser(multipartFile, member);

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, token));
    }

}
