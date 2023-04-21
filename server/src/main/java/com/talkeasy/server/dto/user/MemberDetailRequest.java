package com.talkeasy.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailRequest {
    private String name;
    private String email;
    private String imageUrl;
    private String birthDate;
    private Integer gender;
    private Integer age;
    private Integer role;
    private String accessToken;

}
