package com.talkeasy.server.service.member;

import com.talkeasy.server.config.s3.S3Uploader;
import com.talkeasy.server.domain.Member;
import com.talkeasy.server.dto.user.MemberInfoUpdateRequest;
import com.talkeasy.server.repository.member.MembersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MembersRepository memberRepository;
    private final MongoTemplate mongoTemplate;
    private final S3Uploader s3Uploader;
    public Member getUserInfo(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member findUserByEmail(String email) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("email").is(email)), Member.class);
    }

    public Member updateUserInfo(MultipartFile multipartFile, MemberInfoUpdateRequest request, String memberId){
        Member member = mongoTemplate.findOne(
                Query.query(Criteria.where("id").is(memberId)), Member.class);
        try {
            log.info("============file: " + multipartFile);
            String saveFileName = s3Uploader.uploadFiles(multipartFile, "talkeasy");
            member.setUserInfo(request, saveFileName);
        } catch (Exception e) {}
        return memberRepository.save(member);

    }

    public void saveUser(Member member) {
        memberRepository.save(member);
    }
}
