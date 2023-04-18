package com.talkeasy.server.service.chat;

import com.talkeasy.server.domain.chat.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final MongoTemplate mongoTemplate;

    public Object createChatRoom(Long sendId, Long receiveId, String title) {
        ChatRoom chatRoom = new ChatRoom(new Long[]{sendId, receiveId}, title, LocalDateTime.now().toString());
        ChatRoom newRoom = mongoTemplate.insert(chatRoom);



        return newRoom.getId();
    }
}
