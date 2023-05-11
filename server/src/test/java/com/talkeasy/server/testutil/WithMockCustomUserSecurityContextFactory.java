package com.talkeasy.server.testutil;

import com.talkeasy.server.authentication.OAuthUserInfo;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;


public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Member member = Member.builder()
                .id("1")
                .name("name")
                .email("email@email.com")
                .age(25)
                .role(0)
                .build();

        System.out.println("hihihiiihih");
        OAuth2UserImpl principal = new OAuth2UserImpl(member);
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }

}
