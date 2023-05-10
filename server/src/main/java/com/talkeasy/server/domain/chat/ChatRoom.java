package com.talkeasy.server.domain.chat;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Document("chat_room")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

    //id는 소문자로 설정해야함
    @Id
    private String id;
    private String[] users;
    private String title; // 채팅방 이름
    private String date; // 생성 시간?
    private String leaveUserId; // 채팅방 떠난 사용자
    private String leaveTime; // 채팅방 떠난 시간
    // 테스트용
    private Map<String, UserData> chatUsers = new HashMap<>();

    public ChatRoom(String[] users, String title, String date) {
        this.users = users;
        this.title = title;
        this.date = date;
//        chatUsers = new HashMap<>();
        chatUsers.put(users[0], new UserData(true, null));
        chatUsers.put(users[1], new UserData(true, null));
    }

}
//