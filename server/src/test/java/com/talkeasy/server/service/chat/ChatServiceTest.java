package com.talkeasy.server.service.chat;

import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.domain.chat.ChatRoom;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.domain.chat.UserData;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.service.member.FollowService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @InjectMocks
    private ChatService chatService;

    @Mock
    private MongoTemplate mongoTemplate;

    @Test
    void createRoom() {
    }

    @Test
    void doCreateRoomChat() {
    }

    @Test
    void createQueue() {
    }

    @Test
    void createQueueDetail() {
    }

    @Test
    void convertChat() {
    }

    @Test
    void saveChat() {
    }

    @Test
    void doChat() {
    }

    @Test
    @DisplayName("채팅 내역 불러오기 - 정상(leaveTime 내역이 없을 경우)")
    void getChatHistory() {

        Map<String, UserData> chatUsers = new HashMap<>();
        chatUsers.put("1", new UserData(true, null));
        chatUsers.put("2", new UserData(true, null));

        ChatRoom chatRoom = ChatRoom.builder().id("3").users(new String[]{"1", "2"}).chatUsers(chatUsers).date("2023.01.01").build();
        Mockito.when(mongoTemplate.findOne(any(Query.class), eq(ChatRoom.class))).thenReturn(chatRoom);

        List<ChatRoomDetail> chatRoomDetails = new ArrayList<>();
        chatRoomDetails.add(ChatRoomDetail.builder().roomId("3").created_dt("2023.05.01").build());
        Mockito.when(mongoTemplate.find(any(Query.class), eq(ChatRoomDetail.class))).thenReturn(chatRoomDetails);

        PagedResponse<ChatRoomDetail> response = chatService.getChatHistory("3", 1, 10, "1");

        verify(mongoTemplate, times(1)).findOne(any(Query.class), eq(ChatRoom.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(ChatRoomDetail.class));

        assertThat(response.getData().size()).isEqualTo(1);
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getTotalPages()).isEqualTo(1);

    }

    @Test
    void getChatRoomList() {
    }

    @Test
    void saveLastChat() {
    }

    @Test
    void saveLastChatDetail() {
    }

    @Test
    void getLastChatList() {
    }

    @Test
    void deleteRoom() {
    }

    @Test
    void getUserInfoByRoom() {
    }
}