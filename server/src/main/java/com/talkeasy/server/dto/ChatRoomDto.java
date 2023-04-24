package com.talkeasy.server.dto;

import com.talkeasy.server.domain.chat.ChatRoom;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ChatRoomDto {
    private String roomId;
    private String sendId;
    private String receiveId;
    private String title;

    public ChatRoomDto (ChatRoom chatRoom){
        this.roomId = chatRoom.getId();
        this.sendId = chatRoom.getUsers()[0];
        this.receiveId = chatRoom.getUsers()[1];
        this.title = chatRoom.getTitle();
    }
}
