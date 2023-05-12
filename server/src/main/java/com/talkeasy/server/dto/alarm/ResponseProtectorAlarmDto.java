package com.talkeasy.server.dto.alarm;

import com.talkeasy.server.domain.alarm.Alarm;

public class ResponseProtectorAlarmDto {
    private String alarmId;
    private String content;
    private String time;
    private Boolean readSatus; // 읽음 여부 true/false

    public ResponseProtectorAlarmDto(Alarm alarm) {
        this.alarmId = alarm.getId();
        this.content = alarm.getContent();
        this.time = alarm.getTime();
        this.readSatus = alarm.getReadSatus();
    }
}
