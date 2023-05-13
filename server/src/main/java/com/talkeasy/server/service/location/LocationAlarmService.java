package com.talkeasy.server.service.location;

import com.talkeasy.server.dto.location.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LocationAlarmService {
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    //채팅방 불러오기
    public List<ChatRoom> findAllRoom() {
        //채팅방 최근 생성 순으로 반환
        List<ChatRoom> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);

        return result;
    }

    //채팅방 하나 불러오기
    public ChatRoom findById(String roomId) {
        return chatRooms.get(roomId);
    }


    //채팅방 생성
    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }

    public ChatRoom deleteRoom(String roomId){
        chatRooms.remove(roomId);
        return findById(roomId);
    }

//    public AlarmDto send(AlarmDto alarm){
//        if(AlarmDto.MessageType.SOS.equals(alarm.getType())) {
//            alarm.setCount(alarm.getCount() + 1);
//        }else if(AlarmDto.MessageType.ACCEPT.equals(alarm.getType())){
//            if(alarm.getCount() == 1){ // 정상적으로 열람 시작
//
//                alarm.setCount(alarm.getCount() + 1);
//            }
//
//        }else if(AlarmDto.MessageType.END.equals(alarm.getType())){
//
//        }
//        return alarm;
//    }
}
