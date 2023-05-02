package com.talkeasy.server.domain;

import com.talkeasy.server.dto.user.MemberInfoUpdateRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
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
    private Boolean deleteStatus; // true:탈퇴, false:미탈퇴

    public Member setUserInfo(MemberInfoUpdateRequest request, String imageUrl){
        this.name = request.getName();
        this.gender = request.getGender();
        this.birthDate = request.getBirthDate();
        this.imageUrl = imageUrl != null ? imageUrl : "";
        return this;
    }

    public void setDelete() {
        this.deleteStatus = true;
        this.name = null;
        this.gender = null;
        this.birthDate = null;
        this.imageUrl = null;

    }
}
