package com.talkeasy.server.domain.chat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("chat_room_detail")
@Getter
@Setter
public class ChatRoomDetail {
    @Id
    private String Id;
    private String roomId;
    private String sender;
    private String msg; // 메시지 내용
    private String created_dt; // 생성 시간?
    private String updated_dt; // 생성 시간?

    public ChatRoomDetail(String users, String roomId, String msg, String created_dt, String updated_dt) {
        this.sender = users;
        this.roomId = roomId;
        this.msg = msg;
        this.created_dt = created_dt;
        this.updated_dt = updated_dt;
    }
}