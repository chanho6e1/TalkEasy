package com.talkeasy.server.service.chat;

import com.talkeasy.server.domain.chat.ChatRoom;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final MongoTemplate mongoTemplate;
    private final KafkaTemplate<String, MessageDto> kafkaTemplate;

    public String createChatRoom(Long sendId, Long receiveId, String title) {
        ChatRoom chatRoom = new ChatRoom(new Long[]{sendId, receiveId}, title, LocalDateTime.now().toString());
        ChatRoom newRoom = mongoTemplate.insert(chatRoom);

        return newRoom.getId();

 }


    public Object sendMessage(MessageDto messageDto) {

        log.info("send Message : " + messageDto.getMsg());
        kafkaTemplate.send(messageDto.getRoomId(), messageDto);

        ChatRoomDetail chatRoom = new ChatRoomDetail(messageDto.getSender(), messageDto.getRoomId(), messageDto.getMsg(), LocalDateTime.now().toString(), LocalDateTime.now().toString());
        ChatRoomDetail newChat = mongoTemplate.insert(chatRoom);

        return newChat.getId();
    }
}