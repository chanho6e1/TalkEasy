package com.talkeasy.server.domain.location;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;

@Data
@Document(collection = "sos")
@Builder
public class SOS {
    @Id
    private String id;
    private String title;

    @Field("createdTime")
    @CreatedDate
    private LocalDateTime createdTime;
    @Field("updatedTime")
    @LastModifiedDate
    private LocalDateTime updatedTime;


}
