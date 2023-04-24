package com.talkeasy.server.service.chat;

import com.talkeasy.server.dto.MessageDto;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

//사용 안함

@Service
public class ChatRoomService {

    private final Map<String, List<WebSocketSession>> chatRooms = new ConcurrentHashMap<>();
    private final Map<String, List<MessageDto>> chatMessages = new ConcurrentHashMap<>();

    public void addUserToChatRoom(String roomId, WebSocketSession session) {
        // 새로운 세션이 채팅방에 접속했을 때 호출됩니다.
        // 해당 채팅방에 세션을 추가합니다.
        chatRooms.computeIfAbsent(roomId, k -> new CopyOnWriteArrayList<>()).add(session);
    }

    public List<String> getUsersInChatRoom(String roomId) {
        // 해당 채팅방에 접속한 유저 목록을 반환합니다.
        List<WebSocketSession> sessions = chatRooms.getOrDefault(roomId, Collections.emptyList());
        return sessions.stream()
                .map(s -> s.getAttributes().get("username").toString())
                .collect(Collectors.toList());
    }

    public List<WebSocketSession> getSessionsInChatRoom(String roomId) {
        // 해당 채팅방에 접속한 모든 세션을 반환합니다.
        return chatRooms.getOrDefault(roomId, Collections.emptyList());
    }

    public void saveChatMessage(String roomId, MessageDto message) {
        // 채팅방에서 전송된 메시지를 저장합니다.
        chatMessages.computeIfAbsent(roomId, k -> new CopyOnWriteArrayList<>()).add(message);
    }

    public List<MessageDto> getChatMessages(String roomId) {
        // 해당 채팅방에서 전송된 모든 메시지를 반환합니다.
        return chatMessages.getOrDefault(roomId, Collections.emptyList());
    }
}
