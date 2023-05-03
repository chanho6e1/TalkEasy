package com.talkeasy.server.domain.member;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document(collection = "follow")
@Builder
public class Follow {
    @Id
    private String id;
    private String fromUserId;
    private String toUserId;
    private String memo;
    private Boolean MainStatus;
    private Boolean locationStatus;

}
