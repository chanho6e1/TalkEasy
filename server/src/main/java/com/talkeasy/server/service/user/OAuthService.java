package com.talkeasy.server.service.user;

import com.nimbusds.oauth2.sdk.token.Token;
//import com.talkeasy.server.authentication.JwtTokenProvider;
import com.talkeasy.server.repository.member.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuthService {
    private final MembersRepository membersRepository;
//    private final JwtTokenProvider jwtTokenProvider;

//    @Transactional
//    public Token login(String accessToken){
//        // 프론트에서 받아온 accessToken
//    }
}
