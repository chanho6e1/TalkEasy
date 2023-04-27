package com.talkeasy.server.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Service
@RequiredArgsConstructor
public class ChatUserService {

    private final MongoTemplate mongoTemplate;
    private final RabbitTemplate rabbitTemplate;
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
