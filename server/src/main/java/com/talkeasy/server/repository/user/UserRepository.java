package com.talkeasy.server.repository.user;

import com.talkeasy.server.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
}
