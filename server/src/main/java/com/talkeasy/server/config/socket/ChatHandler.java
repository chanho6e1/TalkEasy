package com.talkeasy.server.config.socket;

import com.talkeasy.server.dto.MessageDto;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.Set;


//????
@Component
public class ChatHandler extends TextWebSocketHandler {

    // 채팅방에 들어온 WebSocketSession을 저장할 Set
    private static final Set<WebSocketSession> chatSessions = new HashSet<>();

    // WebSocket 연결 시 실행되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("hihi");
        // 채팅방에 들어온 WebSocketSession을 Set에 추가
        chatSessions.add(session);
    }

    // WebSocket 메시지 수신 시 실행되는 메서드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 채팅방에 있는 모든 WebSocketSession에 메시지 전송
        for (WebSocketSession chatSession : chatSessions) {
            if (!chatSession.equals(session)) { // 수신 대상 WebSocketSession이 자신이 아닌 경우
                chatSession.sendMessage(message);
            }
        }
    }

    // WebSocket 연결 종료 시 실행되는 메서드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 채팅방에서 나간 WebSocketSession을 Set에서 제거
        System.out.println("bye");

        chatSessions.remove(session);
    }

    // 채팅방에 들어와 있는지 확인하는 메서드
    private boolean isInChatRoom(WebSocketSession session) {
        return chatSessions.contains(session);
    }

    // 알림 전송 메서드
    private void sendNotificationIfNotInChatRoom(MessageDto messageDto) throws Exception {
        // 채팅방에 있는 WebSocketSession 중 메시지 수신 대상이 아닌 WebSocketSession에 알림 전송
        for (WebSocketSession chatSession : chatSessions) {
//            if (!chatSession.equals(session) && !isInChatRoom(chatSession)) {
//                TextMessage notification = new TextMessage("알림: " + messageDto.getMsg());
//                chatSession.sendMessage(notification);
//            }
        }
    }

}