package com.talkeasy.server.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.talkeasy.server.service.user.MemberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Api(tags = {"Member 관련 API"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MemberController {
    private final MemberService memberService;
//    private final OAuthService oAuthService;

    //    @Operation(summary = "login of user", description = "로그인")
//    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Token.class)))})
//    @Parameters({@Parameter(name = "provider", description = "Name of provider", example = "kakao, naver, google")})
    @GetMapping("/login/oauth")
    public ResponseEntity<?> login(@RequestBody String accessToken) { // 인가 코드
//        Token token = oAuthService.login(provider, code);
        String email;
        try {
//            email = getInfo(accessToken);
            email = "wldnjs4255@naver.com";
            log.info("이메일을 받아왔어요!");

            if (memberService.findUserByEmail(email) != null) {
                log.info("이미 데이터베이스에 아이디(login_id)가 있어요");
//                res.put("code", "member");
//                String token = makeToken(email);
//                res.put("result", token);
//                httpStatus = HttpStatus.OK;
            } else {
                log.info("데이터 베이스에 아이디(login_id)가 없어요. 닉네임을 등록하세요");
//                res.put("code", "no_email");
//                res.put("result", email);
//                httpStatus = HttpStatus.LOCKED; //423
            }
        } catch (Exception e) {
//            res.put("code", e.getMessage());
//            httpStatus = HttpStatus.EXPECTATION_FAILED; //417 이에요
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    String getInfo(String token) throws IOException {
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        URL url;
        try {
            url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token);
            int responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";
            if (hasEmail) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }
            System.out.println(element.getAsJsonObject().get("kakao_account").getAsJsonObject());
            return email;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "no_email";
        }
    }
}
