package com.talkeasy.server.dto;

import lombok.Getter;

@Getter
public class MessageDto {
    private String roomId;
    private String sender;
    private String msg; // 메시지 내용
    private String created_dt; // 생성 시간?
}
