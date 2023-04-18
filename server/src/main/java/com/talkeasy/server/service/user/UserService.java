package com.talkeasy.server.service.user;

import com.talkeasy.server.domain.Member;
import com.talkeasy.server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    public Member getUserInfo(String email) {
        return userRepository.findByEmail(email);
    }
}
