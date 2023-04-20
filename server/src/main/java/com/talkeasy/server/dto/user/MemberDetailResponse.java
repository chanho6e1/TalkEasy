package com.talkeasy.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailResponse {

    private String userName;
    private String email;
    private String imageUrl;
    private Integer role; //0:보호자/1:피보호자
    private Integer gender; //0:남/1:여
    private Integer age;
    private String accessToken;



}

