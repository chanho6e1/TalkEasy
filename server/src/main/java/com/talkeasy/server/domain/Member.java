package com.talkeasy.server.domain;

import com.talkeasy.server.dto.user.MemberInfoUpdateRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "member")
@Builder
public class Member {
    @Id
    private String id;
    private String name;
    private String email;
    private String imageUrl;
    private Integer role; //0:보호자/1:피보호자
    private Integer gender; //0:남/1:여
    private Integer age;
    private String birthDate;

    public Member setUserInfo(MemberInfoUpdateRequest request, String imageUrl){
        this.name = request.getName();
        this.gender = request.getGender();
        this.birthDate = request.getBirthDate();
        this.imageUrl = imageUrl != null ? imageUrl : "";
        return this;
    }

}
