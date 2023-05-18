package com.talkeasy.server.dto.chat;

import com.querydsl.codegen.Serializer;
import com.talkeasy.server.domain.alarm.Alarm;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class MessageDto{
    private String msgId;
    private String roomId;
    private String toUserId;
    private String fromUserId;
    private String msg; // 메시지 내용
    private String created_dt; // 생성 시간?
    private int type; // 0(msg) :: 1(location) :: 2(sos)
    private int status; //0(REQUEST) :: 1(RESULT) :: 2(REJECT)
    private String fromUserName;

    public MessageDto(ChatRoomDetail chatRoomDetail, String name){
        this.msgId = chatRoomDetail.getId();
        this.roomId  = chatRoomDetail.getRoomId();
        this.toUserId = chatRoomDetail.getToUserId();
        this.fromUserId = chatRoomDetail.getFromUserId();
        this.msg = chatRoomDetail.getMsg();
        this.created_dt = chatRoomDetail.getCreated_dt();
        this.type = chatRoomDetail.getType();
        this.status = chatRoomDetail.getStatus();
        this.fromUserName = name;
    }
    public MessageDto(Alarm alarm, String name){
//        this.msgId = alarm.getId();
//        this.roomId  = alarm.getRoomId();
//        this.toUserId = alarm.getToUserId();
//        this.fromUserId = alarm.getFromUserId();
        this.msg = alarm.getContent();
        this.created_dt = alarm.getCreatedTime().toString();
        this.type = 2;
        this.status = 1;
        this.fromUserName = name;
    }

}
