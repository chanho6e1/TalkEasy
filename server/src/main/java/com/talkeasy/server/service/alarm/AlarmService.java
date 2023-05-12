package com.talkeasy.server.service.alarm;

import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.common.exception.ArgumentMismatchException;
import com.talkeasy.server.domain.alarm.Alarm;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.dto.alarm.RequestSosAlarmDto;
import com.talkeasy.server.dto.alarm.ResponseProtectorAlarmDto;
import com.talkeasy.server.dto.alarm.ResponseWardAlarmDto;
import com.talkeasy.server.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {

    private final MongoTemplate mongoTemplate;
    private final ChatService chatService;

    // 보호자용 알람 전체 조회
    public PagedResponse<?> getAlarms(Member member) {

        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);

        List<Alarm> alarms = mongoTemplate.find(Query.query(Criteria.where("userId").is(member.getId())
                .and("createdTime").gte(oneWeekAgo)
        ), Alarm.class);


        if (member.getRole() == 0) { // 보호자
            List<ResponseProtectorAlarmDto> result = alarms.stream().map((a) -> new ResponseProtectorAlarmDto(a)).collect(Collectors.toList());
            return new PagedResponse(HttpStatus.OK, result, 1);
        }

        List<ResponseWardAlarmDto> result = alarms.stream().map((a) -> new ResponseWardAlarmDto(a)).collect(Collectors.toList());
        return new PagedResponse(HttpStatus.OK, result, 1);
    }

    // 알람 읽음 처리
    public Boolean putReadStatusByAlarmId(String alarmId, Member member) {

        Alarm alarm = mongoTemplate.findOne(Query.query(Criteria.where("id").is(alarmId)), Alarm.class);

        isMine(alarm.getUserId(), member.getId());

        alarm.setReadStatus(true);
        mongoTemplate.save(alarm);

        return alarm.getReadStatus();
    }

    private void isMine(String myId, String targetId) {

        if (!myId.equals(targetId)) {
            throw new ArgumentMismatchException("나의 알람이 아닙니다.");
        }
    }

    public String postAlarmBySOS(RequestSosAlarmDto requestSosAlarmDto, Member member) {

        Alarm alarm = Alarm.builder()
                .readStatus(false)
                .userId("645307321511deecd5c5441a")
                .content(requestSosAlarmDto.getTime() + "에 도움 요청 버튼이 눌렸습니다.")
                .fromName("ㅇㄹㅇ")
                .build();
//                Alarm alarm = Alarm.builder()
//                .readStatus(false)
//                .userId(member.getId())
//                .content(requestSosAlarmDto.getTime() + "에 도움 요청 버튼이 눌렸습니다.")
//                .fromName(member.getName())
//                .build();

        return chatService.saveAlarm(alarm);

    }
}
