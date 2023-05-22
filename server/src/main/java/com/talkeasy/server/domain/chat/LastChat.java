package com.talkeasy.server.domain.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("last_chat")
@Getter
@Setter
@NoArgsConstructor
public class LastChat {
    @Id
    private String id;
    private String userId;
    private String roomId;
    private String msg; // 메시지 내용
    private String created_dt; // 생성 시간?
    private Integer readCnt; // 읽음 수정
    private String toUserId;
    private String fromUserId;

    public LastChat(ChatRoomDetail chat) {
        this.roomId = chat.getRoomId();
        this.msg = chat.getMsg();
        this.created_dt = chat.getCreated_dt();
        this.readCnt = chat.getReadCnt();
        this.fromUserId = chat.getFromUserId();
        this.toUserId = chat.getToUserId();
    }
}
