package com.talkeasy.server.dto.alarm;

import com.talkeasy.server.domain.alarm.Alarm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class ResponseWardAlarmDto {
    private String alarmId;
    private String roomId;
    private String chatId;
    private String content;
    private String created_dt;
    private Boolean readStatus; // 읽음 여부 true/false
    private String fromUserName; // 요청을 보낸 사람


    public ResponseWardAlarmDto(Alarm alarm) {
        this.alarmId = alarm.getId();
        this.roomId = alarm.getRoomId();
        this.chatId = alarm.getChatId();
        this.content = alarm.getContent();
        this.readStatus = alarm.getReadStatus();
        this.fromUserName = alarm.getFromName();
        this.created_dt = alarm.getCreatedTime().toString();
    }


}
