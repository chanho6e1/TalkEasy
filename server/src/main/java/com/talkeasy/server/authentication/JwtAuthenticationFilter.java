package com.talkeasy.server.authentication;

import com.talkeasy.server.authentication.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.security.SignatureException;
import java.util.HashMap;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String token = tokenProvider.resolveToken(servletRequest);

        if (tokenProvider.validateToken(token)) {
            try {
                setAuthToSecurityContextHolder(token);
            } catch (Exception e) {
                log.error("토큰에 해당하는 사용자가 없습니다.", e);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setAuthToSecurityContextHolder(String token) {
        Authentication auth = tokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}