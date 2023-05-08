package com.talkeasy.server.service.member;

import com.talkeasy.server.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberService userService;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = userService.findUserById(id);

        if (member == null) {
            throw new UsernameNotFoundException(String.format("No user found with userId '%s'.", id));
        } else {
            return new OAuth2UserImpl(member);
        }
    }


}
