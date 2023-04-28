package com.talkeasy.server.dto.chat;

import lombok.Data;

@Data
public class ChatReadDto {
    private String roomId;
    private String readUserId;
    private String readTime;
}
