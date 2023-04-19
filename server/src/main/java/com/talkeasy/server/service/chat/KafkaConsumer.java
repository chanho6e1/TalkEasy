package com.talkeasy.server.service.chat;

import com.talkeasy.server.dto.MessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumer {

//    @KafkaListener(topics = "exam-topic",  containerFactory = "kafkaListenerContainerFactory")
    @KafkaListener(topics = "exam-topic")
    public void consume(MessageDto message) throws IOException {
        System.out.printf("Consumed message : %s", message.getMsg());
    }
}