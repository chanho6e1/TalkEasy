package com.talkeasy.server.dto;

import com.talkeasy.server.domain.chat.ChatRoom;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatRoomResponseDto {

    private String roomId;
    private String userId;
    private String title; // 채팅방 이름
    private String date; // 생성 시간?
    private int notRadCnt; // 안읽음 카운트
    private String nickname;
    private String profile;
    private String content;

    public static ChatRoomResponseDto of(ChatRoom chatRoom, int notRadCnt) {
        ChatRoomResponseDto chatRoomResponseDto = new ChatRoomResponseDto(chatRoom);
        chatRoomResponseDto.setNotRadCnt(notRadCnt);
        return chatRoomResponseDto;
    }

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        roomId = chatRoom.getId();
        this.userId = chatRoom.getUsers()[0];
        this.title = chatRoom.getTitle();
        this.date = chatRoom.getDate();
    }
}
