package com.talkeasy.server.dto.chat;

import com.talkeasy.server.domain.chat.ChatRoom;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatRoomDetailResponseDto {

    private String id;
    private String[] users;
    private String title; // 채팅방 이름
    private String date; // 생성 시간?
    private Long notRadCnt; // 안읽음 카운트

    public static ChatRoomDetailResponseDto of(ChatRoom chatRoom, Long notRadCnt) {
        ChatRoomDetailResponseDto chatRoomResponseDto = new ChatRoomDetailResponseDto(chatRoom);
        chatRoomResponseDto.setNotRadCnt(notRadCnt);
        return chatRoomResponseDto;
    }

    public ChatRoomDetailResponseDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.users = chatRoom.getUsers();
        this.title = chatRoom.getTitle();
        this.date = chatRoom.getDate();
    }
}
