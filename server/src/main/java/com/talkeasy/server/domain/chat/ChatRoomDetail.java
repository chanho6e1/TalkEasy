package com.talkeasy.server.domain.chat;

import com.talkeasy.server.dto.MessageDto;
import lombok.Data;
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
@Data
public class ChatRoomDetail {

    @Id
    private String id;
    private String roomId;
    private String msg; // 메시지 내용
    private String created_dt; // 생성 시간?
    private String updated_dt; // 생성 시간?
    private boolean readStatus; // 생성 시간?
    private String toUserId;
    private String fromUserId;



    public ChatRoomDetail(MessageDto messageDto) {
        this.toUserId = messageDto.getToUserId();
        this.fromUserId = messageDto.getFromUserId();
        this.roomId = messageDto.getRoomId();
        this.msg = messageDto.getMsg();
        this.created_dt = messageDto.getCreated_dt();
        this.updated_dt = now().toString();
    }
}