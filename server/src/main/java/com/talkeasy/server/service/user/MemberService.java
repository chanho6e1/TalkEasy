package com.talkeasy.server.service.user;

import com.talkeasy.server.domain.Member;
import com.talkeasy.server.repository.member.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        System.out.println("여기 들어옴");
        List<Member> member = mongoTemplate.findAll(Member.class, "MEMBER");
//        Object member = mongoTemplate.findOne(
//                Query.query(Criteria.where("email").is(email)), Member.class);
        System.out.println("size : "+member.size());
        System.out.println("size : ");

//        System.out.println(member.get(0).getEmail());
//        System.out.println(member.getEmail());
        return null;
    }
}
