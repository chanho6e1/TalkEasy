package com.talkeasy.server.dto.user;

import com.talkeasy.server.domain.member.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDetailResponse {

    private String id;
    private String userName;
    private String email;
    private String imageUrl;
    private Integer role; //0:보호자/1:피보호자
    private Integer gender; //0:남/1:여
    private Integer age;
    private String birthDate;

    public MemberDetailResponse(Member member) {
        this.id = member.getId();
        this.userName = member.getName();
        this.email = member.getEmail();
        this.imageUrl = member.getImageUrl();
        this.role = member.getRole();
        this.gender = member.getGender();
        this.age = member.getAge();
        this.birthDate = member.getBirthDate();
    }
}

