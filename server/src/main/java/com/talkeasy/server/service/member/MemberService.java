package com.talkeasy.server.service.member;

import com.talkeasy.server.domain.Member;
import com.talkeasy.server.repository.member.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MembersRepository memberRepository;
    private final MongoTemplate mongoTemplate;

    public Member getUserInfo(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member findUserByEmail(String email) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("email").is(email)), Member.class);
    }

    public void saveUser(Member member) {
        memberRepository.save(member);
    }
}
