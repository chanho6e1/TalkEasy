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
    private String content;
    private String time;
    private Boolean readStatus; // 읽음 여부 true/false
    private int type; // location : 1, sos : 2;
    private String chatId; //채팅 번호
    private String fromUserName; // 요청을 보낸 사람

    public ResponseWardAlarmDto(Alarm alarm) {
        this.alarmId = alarm.getId();
        this.content = alarm.getContent();
        this.time = alarm.getTime();
        this.readStatus = alarm.getReadStatus();
        this.type = alarm.getType();
        this.chatId = alarm.getChatId();
        this.fromUserName = alarm.getFromName();
    }
}
