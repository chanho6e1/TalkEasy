package com.talkeasy.server.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDto {
    public enum MessageType {
        SOS, ACCEPT, END
    }

    private AlarmDto.MessageType type;
    //채팅방 ID
    private String roomId;
    //보내는 사람
    private String sender;
    private int count;
    //내용
    private String message;
}
