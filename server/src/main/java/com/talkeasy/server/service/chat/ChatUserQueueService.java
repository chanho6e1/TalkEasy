package com.talkeasy.server.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatUserQueueService {

    private final AmqpAdmin amqpAdmin;

    public String createUserQueue(String userId) { //채팅방(최신 메시지가 담긴) 채팅방 목록 확인을 위해
        StringBuilder sb = new StringBuilder();
        sb.append("user.").append(userId);
        Queue queue = QueueBuilder.durable("user.queue." + userId).build();
        amqpAdmin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue)
                .to(new TopicExchange("user.exchange"))
                .with(sb.toString());
        amqpAdmin.declareBinding(binding);

        return userId;

    }
}
