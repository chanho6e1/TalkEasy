package com.talkeasy.server.domain.aac;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("custom_aac")
@Builder
@Getter
@Setter
public class CustomAAC {
    @Id
    private String id;
    private String userId;
    private String text;
}
