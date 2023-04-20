package com.talkeasy.server.service.member;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.talkeasy.server.authentication.JwtTokenProvider;
import com.talkeasy.server.repository.member.MembersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OAuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public String getToken(String accessToken) {
        log.info("========== AccessToken : {} ", accessToken);

        String email = null;
        String token;

        try {
            email = getEmail(accessToken);
            log.info("========== getInfo email : {}", email);
        } catch (IOException e) {
            log.info("========== exception 발생 : {} ", e.getMessage());
        }

        if (memberService.findUserByEmail(email) == null) {
            log.info("========== 데이터 베이스에 아이디(login_id)가 없다.");
            throw new NullPointerException();
        }
        token = jwtTokenProvider.createAccessToken(email);
        log.info("========== jwt Token : {} ", token);

        return token;
    }

    public String getEmail(String token) throws IOException {
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        URL url;
        try {
            url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new NotFoundException("token 에러");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            JsonElement element = JsonParser.parseString(sb.toString());

            String email = "";
            if (element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean()) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }
            return email;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "no_email";
        }

    }


}
