package com.talkeasy.server.domain.aac;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("aac")
@Data
@Builder
public class AAC {
    @Id
    private String id;
    private String title; // aac 내용
    private String category; //카테고리 아이디
    private Integer noun; //명사면 1
    private String relative_verb; // 관련 동사들
    private Integer fixed; // 고정여부인지

}
