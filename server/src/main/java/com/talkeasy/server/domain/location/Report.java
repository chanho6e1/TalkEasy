package com.talkeasy.server.domain.location;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "report")
@Getter
public class Report {
    @Id
    private String id;
    private String userId;
    private LocalDateTime datetime;
    private Double lat;
    private Double lon;

}
