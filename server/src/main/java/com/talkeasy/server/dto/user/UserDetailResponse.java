package com.talkeasy.server.dto.user;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {

    private String userName;
    private String email;
    private String imageUrl;
    private Integer role; //0:보호자/1:피보호자
    private Integer gender; //0:남/1:여
    private Integer age;



}

