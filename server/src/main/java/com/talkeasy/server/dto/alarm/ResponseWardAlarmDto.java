package com.talkeasy.server.dto.alarm;

import com.talkeasy.server.domain.alarm.Alarm;

public class ResponseWardAlarmDto {
    private String alarmId;
    private String content;
    private String time;
    private Boolean readStatus; // 읽음 여부 true/false
    private int type; // location : 1, sos : 2;
    private String chatId; //채팅 번호

    public ResponseWardAlarmDto(Alarm alarm) {
        this.alarmId = alarm.getId();
        this.content = alarm.getContent();
        this.time = alarm.getTime();
        this.readStatus = alarm.getReadStatus();
        this.type = alarm.getType();
        this.chatId = alarm.getChatId();
    }
}
