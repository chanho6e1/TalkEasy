//package com.talkeasy.server.controller.chat;
//
//import com.talkeasy.server.dto.MessageDto;
//import lombok.RequiredArgsConstructor;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class KafkaConsumerListener {
//
//    private final SimpMessagingTemplate messagingTemplate;
//    private final KafkaTemplate<String, MessageDto> kafkaTemplate;
//
//    @KafkaListener(topics = "64411ac7fe7e9b41ad3cb0ae", concurrency = "3")
//    public void listen(ConsumerRecord<String, MessageDto> record) {
//        System.out.println("ByListener :: Received message from Kafka: " + record.value());
//        MessageDto messageDto = record.value();
//
//        messagingTemplate.convertAndSend("/topic/" + messageDto.getRoomId(), messageDto);
////        kafkaTemplate.setMessageConverter("/topic/" + messageDto.getRoomId(), messageDto);
//    }
//}