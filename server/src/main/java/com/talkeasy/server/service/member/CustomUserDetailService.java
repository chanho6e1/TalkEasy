package com.talkeasy.server.service.member;

import com.talkeasy.server.domain.Member;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    MemberService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member= userService.getUserInfo(username);

        if (member == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            UserDetails userDetails = User.builder()
                    .username(member.getUserName())
                    .build();
            return userDetails;

        }
    }
}
