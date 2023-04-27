package com.talkeasy.server.domain.aac;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("aac_category")
@Data
public class AacCategory {
    @Id
    private String id;
    private String title; // 카테고리 이름
}
