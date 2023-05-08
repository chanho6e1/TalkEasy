package com.talkeasy.server.domain.member;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "follow")
@Builder
public class Follow {
    @Id
    private String id;
    private String fromUserId; // 팔로워
    private String toUserId; // 팔로잉
    private String memo;
    private Boolean MainStatus;
    private Boolean locationStatus;

}
