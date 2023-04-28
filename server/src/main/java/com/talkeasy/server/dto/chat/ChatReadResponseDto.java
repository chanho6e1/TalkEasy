package com.talkeasy.server.dto.chat;

import com.talkeasy.server.domain.chat.ChatRoomDetail;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ChatReadResponseDto {
    private String roomId;
    private String msgId;
    private int readCnt;

    public ChatReadResponseDto(ChatRoomDetail chatRoomDetail) {
        this.roomId = chatRoomDetail.getRoomId();
        this.msgId = chatRoomDetail.getId();
        this.readCnt = chatRoomDetail.getReadCnt();
    }
}
