package com.talkeasy.server.domain.alarm;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("alarm")
@Data
@Builder
public class Alarm {

    @Id
    private String id;
//    private String time; //    메시지가 온 시간
    private String content; //메시지 내용
    private int type; //(위치/sos : 1/2)
//    private int status; //type:2 -> 0,1,2
    private String userId; //수신할 유저
    private String roomId;
    private String chatId; //채팅 번호
    private String fromName; //요청을 한 사람 이름

    private String createdTime; //메시지 생성 시간

    private Boolean readStatus; //읾음 처리 여부

}
