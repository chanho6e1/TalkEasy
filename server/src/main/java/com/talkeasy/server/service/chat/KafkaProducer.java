package com.talkeasy.server.service.chat;

import com.talkeasy.server.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private static final String TOPIC = "exam-topic";
    private final KafkaTemplate<String, MessageDto> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MessageDto message) {
        System.out.printf("Produce message : %s", message.getMsg());
        this.kafkaTemplate.send(message.getRoomId(), message);
    }
}
