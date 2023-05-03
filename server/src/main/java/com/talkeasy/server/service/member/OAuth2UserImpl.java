package com.talkeasy.server.service.member;


import com.talkeasy.server.authentication.OAuthUserInfo;
import com.talkeasy.server.domain.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class OAuth2UserImpl implements OAuth2User, UserDetails {

    private final Member member;
    private OAuthUserInfo oAuthUserInfo;


    public OAuth2UserImpl(Member member) {
        this.member = member;
    }

    public OAuth2UserImpl(Member member, OAuthUserInfo oAuthUserInfo) {
        this.member = member;
        this.oAuthUserInfo = oAuthUserInfo;
    }


    public Member getMember() {
        return member;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return member.getName();
    }

//    @Override
//    public String getUsername() {
//        return member.getProviderId();
//    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuthUserInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getRole().toString()));
        return roles;
    }

    @Override
    public String getName() {
        return member.getName();
    }


    public String getId() {
        return member.getId();
    }

    public String getEmail() {
        return member.getEmail();
    }

}
