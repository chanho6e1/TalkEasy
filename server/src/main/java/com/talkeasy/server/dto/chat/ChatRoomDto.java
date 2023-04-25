package com.talkeasy.server.dto.chat;

import com.talkeasy.server.domain.chat.ChatRoom;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ChatRoomDto {
    private String roomId;
    private String fromUserId;
    private String toUserId;
    private String title;

    public ChatRoomDto (ChatRoom chatRoom){
        this.roomId = chatRoom.getId();
        this.fromUserId = chatRoom.getUsers()[0];
        this.toUserId = chatRoom.getUsers()[1];
        this.title = chatRoom.getTitle();
    }
}
