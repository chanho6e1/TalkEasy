//package com.talkeasy.server.service.chat;
//
//import com.talkeasy.server.dto.MessageDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class KafkaProducer {
//    private static final String TOPIC = "exam-topic";
//    private final KafkaTemplate<String, MessageDto> kafkaTemplate;
//
//    public void sendMessage(MessageDto message) {
//        System.out.printf("Produce message : %s", message.getMsg());
//        kafkaTemplate.send(TOPIC, message);
//    }
//}
