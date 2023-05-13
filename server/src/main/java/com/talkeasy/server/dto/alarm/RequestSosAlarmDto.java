package com.talkeasy.server.dto.alarm;

import com.talkeasy.server.domain.alarm.Alarm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestSosAlarmDto {

    private String time;

}
