package com.talkeasy.server.dto.aac;

import com.talkeasy.server.domain.aac.AAC;
import com.talkeasy.server.domain.aac.CustomAAC;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class ResponseAACDto {

    private String id;
    private String title; // aac 내용
    private String category; //카테고리 아이디

    public ResponseAACDto(AAC aac) {
        this.id = aac.getId();
        this.title = aac.getTitle();
        this.category = aac.getCategory();
    }

    public ResponseAACDto(CustomAAC aac) {
        this.id = aac.getId();
        this.title = aac.getText();
        this.category = "9";
    }
}
