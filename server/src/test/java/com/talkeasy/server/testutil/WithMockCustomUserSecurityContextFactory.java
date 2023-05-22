package com.talkeasy.server.testutil;

import com.talkeasy.server.authentication.OAuthUserInfo;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;
import java.util.List;


public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomUser annotation) {

        System.out.println("-------------------------------------------------------------------------------------");

//        SecurityContext context = SecurityContextHolder.createEmptyContext();
        SecurityContext context = SecurityContextHolder.getContext();

        Member member = Member.builder()
                .id(annotation.id())
                .name(annotation.name())
                .role(1)
                .build();

        System.out.println("======================================================================================");

        OAuth2UserImpl principal = new OAuth2UserImpl(member);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                principal,
                principal.getAuthorities()
        );

        context.setAuthentication(authenticationToken);



        return context;
    }

}
