package com.talkeasy.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LastChatResponse {
    private String msg; // 메시지 내용
    private String created_dt; // 생성 시간?
    private Integer readCnt; // 읽음 수정
}
