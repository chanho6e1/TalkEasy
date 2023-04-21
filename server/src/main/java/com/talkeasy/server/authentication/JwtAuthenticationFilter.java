package com.talkeasy.server.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String token = tokenProvider.resolveToken(servletRequest);
        log.info("========== token : {}", token);

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