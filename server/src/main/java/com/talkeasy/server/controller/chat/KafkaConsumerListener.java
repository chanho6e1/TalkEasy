package com.talkeasy.server.controller.chat;

import com.talkeasy.server.dto.MessageDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

//    @KafkaListener(topics = "64411ac7fe7e9b41ad3cb0ae")
//    public void listen(ConsumerRecord<String, MessageDto> record) {
//        System.out.println("Received message from Kafka: " + record.value());
//        MessageDto messageDto = record.value();
//        messagingTemplate.convertAndSend("/topic/" + messageDto.getRoomId(), messageDto);
//    }
}