package com.talkeasy.server.domain.member;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "follow")
@Builder
public class Follow {
    @Id
    private String id;
    private String fromUserId; // 팔로워
    private String toUserId; // 팔로잉
    private String memo;
    private Boolean mainStatus;
    private Boolean locationStatus;
    private String nickName; // 피보호자가 보호자에게 설정할 별명. 기본값은 ""
    @Field("createdTime")
    @CreatedDate
    private LocalDateTime createdTime;
    @Field("updatedTime")
    @LastModifiedDate
    private LocalDateTime updatedTime;
}
