package com.talkeasy.server.service.chat;

import com.google.gson.Gson;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.dto.chat.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatReadService {

    private final MongoTemplate mongoTemplate;
    private final RabbitTemplate rabbitTemplate;

    public ChatReadDto convertChatRead(Message message) {
        String str = new String(message.getBody());
        ChatReadDto readChat = new Gson().fromJson(str, ChatReadDto.class);

        log.info("{}", readChat);
        return readChat;
    }

    public void readMessage(ChatReadDto chatRead) {

        // 내가 보낸게 아니면서, readCnt = 1, chatRead.getTime보다 작은..
        List<ChatRoomDetail> chatList = mongoTemplate.find(Query.query(Criteria.where("created_dt").lt(chatRead.getReadTime()).and("readCnt").gt(0).and("roomId").is(chatRead.getRoomId())), ChatRoomDetail.class);

        for (ChatRoomDetail chat : chatList) {
            if (!chatRead.getReadUserId().equals(chat.getFromUserId())) {
                if (chat.getReadCnt() > 0) {
                    chat.setReadCnt(0);
                    mongoTemplate.save(chat);

                    Message msg = convertReadMessage(chat);

                    // 바뀐 내용을 다시 채팅방으로 전송
                    StringBuilder queueName = new StringBuilder();
//                    queueName.append("read.queue.").append(chat.getRoomId()).append(".").append(chat.getFromUserId()); // 내꺼
//                    rabbitTemplate.send("read.exchange", queueName.toString(), msg);

                    queueName = new StringBuilder();
                    queueName.append("read.queue.").append(chat.getRoomId()).append(".").append(chat.getToUserId()); // 상대방꺼
                    rabbitTemplate.send("read.exchange", queueName.toString(), msg);

                }
            }
        }


    }

    private Message convertReadMessage(ChatRoomDetail chat) {
        Gson gson = new Gson();
        Message msg = MessageBuilder.withBody(gson.toJson(new ChatReadResponseDto(chat)).getBytes()).build();

        return msg;
    }
}