package com.talkeasy.server.repository.member;

import com.talkeasy.server.domain.member.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends MongoRepository<Member, Long> {
    Member findByEmail(String email);

    int countByEmail(String email);
}
