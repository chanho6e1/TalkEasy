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
public class ResponseProtectorAlarmDto { //보호자용
    private String alarmId;
    private String content;
    private Boolean readStatus; // 읽음 여부 true/false
    private String chatId; //채팅 번호
    private String fromUserName; // 요청을 보낸 사람
    private String created_dt;

    public ResponseProtectorAlarmDto(Alarm alarm) {
        this.alarmId = alarm.getId();
        this.content = alarm.getContent();
        this.readStatus = alarm.getReadStatus();
        this.chatId = alarm.getChatId();
        this.fromUserName = alarm.getFromName();
        this.created_dt = alarm.getCreatedTime().toString();
    }
}
