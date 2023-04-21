package com.talkeasy.server.domain.chat;

import com.talkeasy.server.dto.MessageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static java.time.LocalTime.now;

@Document("chat_room_detail")
@Getter
@Setter
@NoArgsConstructor
public class ChatRoomDetail {
    @Id
    private String id;
    private String roomId;
    private String sender;
    private String msg; // 메시지 내용
    private String created_dt; // 생성 시간?
    private String updated_dt; // 생성 시간?
    private boolean readStatus; // 생성 시간?


    public ChatRoomDetail(String users, String roomId, String msg, String created_dt, String updated_dt) {
        this.sender = users;
        this.roomId = roomId;
        this.msg = msg;
        this.created_dt = created_dt;
        this.updated_dt = updated_dt;
    }

    public ChatRoomDetail(MessageDto messageDto) {
        this.sender = messageDto.getSender();
        this.roomId = messageDto.getRoomId();
        this.msg = messageDto.getMsg();
        this.created_dt = messageDto.getCreated_dt();
        this.updated_dt = now().toString();
    }
}