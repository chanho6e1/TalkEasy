package com.talkeasy.server.service.member;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.talkeasy.server.authentication.JwtTokenProvider;
import com.talkeasy.server.common.exception.NotFoundException;
import com.talkeasy.server.config.s3.S3Uploader;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.dto.user.LoginResponse;
import com.talkeasy.server.dto.user.MemberDetailRequest;
import com.talkeasy.server.service.chat.ChatUserQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OAuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final ChatUserQueueService chatUserQueueService;
    private final S3Uploader s3Uploader;

    public LoginResponse getToken(String accessToken, int role) {
        log.info("========== AccessToken : {} ", accessToken);

        String email = null;
        String token;

        try {
            email = getEmail(accessToken);
            log.info("========== email : {} ", email);

        } catch (IOException e) {
            log.info("========== exception 발생 : {} ", e.getMessage());
        }
        Member member = memberService.findUserByEmailAndRole(email, role);
        if (member == null || member.getRole() != role) {
            log.info("========== 데이터 베이스에 아이디(login_id)가 없다.");
            return null;
        }
        token = jwtTokenProvider.createAccessToken(member.getId());

        return new LoginResponse(member.getName(), token);
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

    public String registerUser(MultipartFile multipartFile, MemberDetailRequest member) {

        String email = null;
        String saveFileName = "";
        try {
            email = getEmail(member.getAccessToken());
        } catch (IOException e) {
            log.info("========== exception 발생 : {}", e.getMessage());
        }
        try {
            log.info("============file: " + multipartFile);
            saveFileName = s3Uploader.uploadFiles(multipartFile, "talkeasy");
        } catch (Exception e) {
            log.info("========== exception 발생 : {}", e.getMessage());
        }
        String userId = null;
        if(member.getRole() == 0){
            userId = memberService.saveUser(Member.builder().name(member.getName()).email(email).imageUrl(saveFileName).role(member.getRole()).build());
        }else {
            userId = memberService.saveUser(Member.builder().name(member.getName()).email(email).imageUrl(saveFileName).role(member.getRole()).gender(member.getGender()).age(calcAge(member.getBirthDate())).birthDate(member.getBirthDate()).build());
        }

        //채팅에 사용할 유저별 큐 생성
        chatUserQueueService.createUserQueue(userId);

        return jwtTokenProvider.createAccessToken(userId);
    }
    private int calcAge(String birthDate){
        LocalDate now = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");

        int nowYear = Integer.parseInt(now.format(formatter));
        int birthYear = Integer.parseInt(birthDate.substring(0,4));

        return nowYear-birthYear+1;
    }
}
