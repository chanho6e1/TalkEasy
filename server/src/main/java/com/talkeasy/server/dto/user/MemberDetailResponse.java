package com.talkeasy.server.dto.user;

import com.talkeasy.server.domain.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDetailResponse {

    private String userName;
    private String email;
    private String imageUrl;
    private Integer role; //0:보호자/1:피보호자
    private Integer gender; //0:남/1:여
    private Integer age;

    public MemberDetailResponse(Member member) {
        this.userName = member.getName();
        this.email = member.getEmail();
        this.imageUrl = member.getImageUrl();
        this.role = member.getRole();
        this.gender = member.getAge();
        this.age = member.getAge();
    }
}

