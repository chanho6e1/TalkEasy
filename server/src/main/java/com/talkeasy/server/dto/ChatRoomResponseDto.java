package com.talkeasy.server.dto;

import com.talkeasy.server.domain.chat.ChatRoom;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatRoomResponseDto {

    private String Id;
    private Long[] users;
    private String title; // 채팅방 이름
    private String date; // 생성 시간?
    private Long notRadCnt; // 안읽음 카운트

    public static ChatRoomResponseDto of(ChatRoom chatRoom, Long notRadCnt) {
        ChatRoomResponseDto chatRoomResponseDto = new ChatRoomResponseDto(chatRoom);
        chatRoomResponseDto.setNotRadCnt(notRadCnt);
        return chatRoomResponseDto;
    }

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        Id = chatRoom.getId();
        this.users = chatRoom.getUsers();
        this.title = chatRoom.getTitle();
        this.date = chatRoom.getDate();
    }
}
