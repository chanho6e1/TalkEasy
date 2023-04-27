package com.talkeasy.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
        @KafkaListener(topics = "exam-topic4", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
//    @KafkaListener(topicPartitions = @org.springframework.kafka.annotation.TopicPartition(topic = "test-topic", partition = {"0"}), groupId = "my-group")
//    @KafkaListener(topicPartitions = {
//            @TopicPartition(topic = "exam-topic4", partitions = {"0"})
//    }, groupId = "my-group", properties = "auto.offset.reset:earliest")
    public void consume(String message) throws IOException {
        log.info("========== Consumed message : {}", message);
    }
}

