package com.talkeasy.server.dto;

import lombok.Data;

@Data
public class ChatRoomListDto {
    int roomId;
    int userId;
    int noReadCnt;
    String nickname;
    String profile;
    String content;
    long date;
}
