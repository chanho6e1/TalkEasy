package com.talkeasy.server.service;

import com.talkeasy.server.dto.chat.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, MessageDto> kafkaTemplate;

//    @Autowired
//    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }//    @Value(value = "${message.topic.name}")
    private String topicName="testTopic";

    public void sendMessage(String message) {
        System.out.println(String.format("Produce message : %s", message));
        MessageDto messageDto = new MessageDto();
        messageDto.setMsg("test1");
        this.kafkaTemplate.send(topicName, messageDto);
    }
}

