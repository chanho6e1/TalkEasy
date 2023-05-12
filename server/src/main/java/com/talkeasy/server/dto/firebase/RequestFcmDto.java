package com.talkeasy.server.dto.firebase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestFcmDto {
    private String userId; // 보낼 유저 아이디
    private String title;
    private String body;
}
