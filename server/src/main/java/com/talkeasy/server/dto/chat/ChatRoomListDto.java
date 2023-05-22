package com.talkeasy.server.dto.chat;

import com.talkeasy.server.domain.chat.LastChat;
import lombok.Data;

@Data
public class ChatRoomListDto {

    String roomId;
    String userId;
    int noReadCnt;
    String name;
    String profile;
    String msg;
    String created_dt;

    public ChatRoomListDto(LastChat lastChat) {

        this.roomId = lastChat.getRoomId();
        this.userId = lastChat.getUserId();
        this.msg = lastChat.getMsg();
        this.created_dt = lastChat.getCreated_dt();

    }
}
