package com.talkeasy.server.controller.member;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.talkeasy.server.authentication.JwtTokenProvider;
import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.domain.Member;
import com.talkeasy.server.service.member.MemberService;
import com.talkeasy.server.service.member.OAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"Member 관련 API"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class OAuthController {
    private final MemberService memberService;
    private final OAuthService oAuthService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "로그인 하기", notes = "access token 을 보내면 유저정보 반환")
    @PostMapping ("/login/oauth")
    public ResponseEntity<CommonResponse> login(@RequestParam (value = "accessToken") String accessToken) { // 인가 코드
        String token = null;
        try {
            token = oAuthService.getToken(accessToken);
        }catch (Exception e) {
//            res.put("code", e.getMessage());
//            httpStatus = HttpStatus.EXPECTATION_FAILED; //417 이에요
        }
        return ResponseEntity.ok().body(CommonResponse.of(
                "로그인 성공", token
        ));
    }
    @ApiOperation(value = "회원정보 등록하기", notes = "회원정보 등록하기")
    @PostMapping("/register")
    ResponseEntity<Map<String, Object>> registerUser(
            @RequestParam("name") String name,
            @RequestParam("imageUrl") String imageUrl,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("gender") Integer gender,
            @RequestParam("age") Integer age,
            @RequestParam("accessToken") String accessToken) throws IOException {
//        userService.saveUser(User.builder().loginId(loginId).name(name).build());

        String email = oAuthService.getInfo(accessToken);
//        log.info("회원저장을 시도할게요:" + v.get("loginId") + " " + name + " " + v.get("profile"));
        memberService.saveUser(Member.builder().userName(name).imageUrl(imageUrl).birthDate(birthDate).gender(gender).age(age).build());
        log.info("회원정보를 데이터 베이스에 저장했어요!");
//        Map res = new HashMap<>();
//        res.put("code", "save_success");
//        res.put("result", makeToken(v.get("loginId")));
        HttpStatus httpStatus = HttpStatus.OK;
//        return new ResponseEntity<>(res, httpStatus);
        return null;
    }


}
