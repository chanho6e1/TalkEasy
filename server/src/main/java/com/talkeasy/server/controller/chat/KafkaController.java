package com.talkeasy.server.controller.chat;

import com.talkeasy.server.dto.MessageDto;
import com.talkeasy.server.service.KafkaConsumerService;
import com.talkeasy.server.service.chat.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/kafka")
public class KafkaController {

//    private final KafkaProducer producer;
//
//    @PostMapping
//    public String sendMessage(@RequestBody MessageDto message) {
//        this.producer.sendMessage(message);
//        return "success";
//    }
//
//    private final KafkaAdmin kafkaAdmin;
//    private final KafkaTemplate<String, MessageDto> kafkaTemplate;
//
//    @PostMapping("/topic/create")
//    public ResponseEntity<String> createTopic(@RequestBody String chatRoomId) {
//        NewTopic newTopic = new NewTopic(chatRoomId, 1, (short) 1);
//        kafkaAdmin.createOrModifyTopics(newTopic);
//        return ResponseEntity.ok("Topic created successfully");
//    }
//
//    @PostMapping("/chat/{chatRoomId}")
//    @MessageMapping("/chat/kafka")
//    public ResponseEntity<String> sendMessage(@PathVariable String chatRoomId, @RequestBody MessageDto message) {
//        kafkaTemplate.send(chatRoomId, message);
//        return ResponseEntity.ok("Message sent successfully");
//    }

    private final KafkaConsumerService kafkaConsumerService;

    @PostMapping("/subscribe/{topic}")
    public ResponseEntity<String> subscribe(@PathVariable String topic) {
        kafkaConsumerService.subscribe(topic);
        return ResponseEntity.ok("Subscribed to topic: " + topic);
    }

    @PostMapping("/unsubscribe/{topic}")
    public ResponseEntity<String> unsubscribe(@PathVariable String topic) {
        kafkaConsumerService.unsubscribe(topic);
        return ResponseEntity.ok("Unsubscribed from topic: " + topic);
    }
}
