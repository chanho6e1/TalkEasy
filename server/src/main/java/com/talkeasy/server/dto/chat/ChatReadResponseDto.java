package com.talkeasy.server.dto.chat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatReadResponseDto {
    private String msgId;
    private String roomId;
    private int readCnt;
}
