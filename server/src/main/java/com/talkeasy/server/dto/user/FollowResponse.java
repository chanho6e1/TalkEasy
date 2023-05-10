package com.talkeasy.server.dto.user;

import com.talkeasy.server.domain.member.Follow;
import com.talkeasy.server.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FollowResponse {

    private String userId;
    private String userName;
    private String imageUrl;
    private Integer gender; //0:남/1:여
    private Integer age;
    private String memo;
    private Boolean MainStatus;
    private Boolean locationStatus;

    public FollowResponse(Member member, Follow follow) {
        this.userId = member.getId();
        this.userName = member.getName();
        this.imageUrl = member.getImageUrl();
        this.memo = follow.getMemo();
        this.MainStatus = follow.getMainStatus();
        this.locationStatus = follow.getLocationStatus();
        this.age = member.getAge();
        this.gender = member.getGender();
    }
}
