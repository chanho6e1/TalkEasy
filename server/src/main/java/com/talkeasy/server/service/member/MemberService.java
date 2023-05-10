package com.talkeasy.server.service.member;

import java.time.LocalDate;

import com.talkeasy.server.config.s3.S3Uploader;
import com.talkeasy.server.domain.aac.CustomAAC;
import com.talkeasy.server.domain.app.UserAppToken;
import com.talkeasy.server.domain.chat.ChatRoom;
import com.talkeasy.server.domain.member.Follow;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.dto.user.MemberInfoUpdateRequest;
import com.talkeasy.server.repository.member.MembersRepository;
import com.talkeasy.server.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MembersRepository memberRepository;
    private final MongoTemplate mongoTemplate;
    private final S3Uploader s3Uploader;
    private final AmqpAdmin amqpAdmin;
    private final ChatService chatService;
    private final FollowService followService;

    public Member getUserInfo(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member findUserByEmailAndRole(String email, int role) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("email").is(email).and("role").is(role)), Member.class);
    }

    public Member findUserById(String id) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("id").is(id)), Member.class);
    }

    public Member updateUserInfo(MultipartFile multipartFile, MemberInfoUpdateRequest request, String memberId) {
        Member member = mongoTemplate.findOne(
                Query.query(Criteria.where("id").is(memberId)), Member.class);
        try {
            log.info("============file: " + multipartFile);
            String saveFileName = s3Uploader.uploadFiles(multipartFile, "talkeasy");
            if (member.getRole() == 0) { // 보호자의 경우
                member.setUserInfo(request, saveFileName);
            } else {  // 피보호자의 경우
                member.setUserInfo(calcAge(request.getBirthDate()), request, saveFileName);
            }
        } catch (Exception e) {
            log.info("========== exception 발생 : {}", e.getMessage());
        }
        return memberRepository.save(member);

    }

    public String saveUser(Member member) {
        Member member1 = memberRepository.save(member);
        return member1.getId();
    }

    /* 회원 탈퇴 : 관련 큐 삭제, 커스텀 AAC 삭제, 유저 앱 토큰 삭제, 채팅방 내부 사용자 정보 "null"으로 변경 */
    public String deleteUserInfo(String userId) throws IOException {

        // 유저 큐 삭제
        deleteUserQueue("user.queue", userId);

        // 채팅 큐 삭제
        List<ChatRoom> chatRoomList = mongoTemplate.find(Query.query(Criteria.where("users").in(userId)), ChatRoom.class);
        for (ChatRoom chatRoom : chatRoomList) {
            chatService.deleteRoom(chatRoom.getId(), userId);
        }

        // Member 테이블에서 삭제
        Member member = mongoTemplate.findOne(Query.query(Criteria.where("id").is(userId)), Member.class);

        mongoTemplate.remove(member);

//        member.setDelete();
//        mongoTemplate.save(member);

        // 커스텀 AAC 삭제
        mongoTemplate.remove(Query.query(Criteria.where("userId").is(userId)), CustomAAC.class);

        // 유저 앱 토큰 삭제
        mongoTemplate.remove(Query.query(Criteria.where("userId").is(userId)), UserAppToken.class);

        return userId;
    }

    private void deleteUserQueue(String queueName, String userId) {
        amqpAdmin.deleteQueue(queueName + "." + userId);
    }

    private int calcAge(String birthDate) {
        LocalDate now = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");

        int nowYear = Integer.parseInt(now.format(formatter));
        int birthYear = Integer.parseInt(birthDate.substring(0, 4));

        return nowYear - birthYear + 1;
    }


    /* 일괄 위치 정보 동의/비동의 */
    public boolean putLocationStatus(String userId) {

        Member member = findUserById(userId);

        member.setLocationStatus(!member.getLocationStatus());
        mongoTemplate.save(member);

        return member.getLocationStatus();
    }

}
