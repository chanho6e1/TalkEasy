package com.talkeasy.server.service.chat;

import com.google.gson.Gson;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.dto.chat.ChatReadDto;
import com.talkeasy.server.dto.chat.ChatReadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatReadService {

    private final MongoTemplate mongoTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final Gson gson;

    public ChatReadDto convertChat(Message message) {
        String str = new String(message.getBody());
        ChatReadDto chat = gson.fromJson(str, ChatReadDto.class);

        return chat;
    }

    public void readMessage(ChatReadDto chatReadDto) {

        List<ChatRoomDetail> chatList = mongoTemplate.find(Query.query(Criteria.where("created_dt").lt(chatReadDto.getReadTime()).and("readCnt").is(1)
                .and("roomId").is(chatReadDto.getRoomId())), ChatRoomDetail.class);

        for(ChatRoomDetail chat : chatList) {
            if (!chatReadDto.getReadUserId().equals(chat.getFromUserId())) {
                if (chat.getReadCnt() > 0) {
                    chat.setReadCnt(0);
                    mongoTemplate.save(chat);

                    log.info("roomId {}", chat.getRoomId());
                    log.info("userId {}", chat.getToUserId());

                    StringBuilder sb = new StringBuilder()
                            .append("room.")
                            .append(chat.getRoomId())
                            .append(".")
                            .append(chat.getToUserId()); // 상대방에게 보내기

                    log.info("sb {}", sb);

                    ChatReadResponseDto chatReadResponseDto = ChatReadResponseDto.builder().msgId(chat.getId()).roomId(chat.getRoomId())
                            .readCnt(chat.getReadCnt()).build();

                    Message msg = MessageBuilder.withBody(gson.toJson(chatReadResponseDto).getBytes()).build();

                    rabbitTemplate.send("read.exchange", sb.toString(), msg);

                }
            }
        }
    }
}
