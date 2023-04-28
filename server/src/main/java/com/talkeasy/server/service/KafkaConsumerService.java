package com.talkeasy.server.service;

import com.talkeasy.server.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
//    @KafkaListener(topics = "topic20", groupId = "my-group", properties = {"auto.offset.reset:earliest"})
//    @KafkaListener(topicPartitions = @org.springframework.kafka.annotation.TopicPartition(topic = "exam-topic5", partition = {"0"}), groupId = "my-group")
//    @KafkaListener(topicPartitions = {
//            @TopicPartition(topic = "topic20", partitions = {"0", "1"})
//    }, groupId = "my-group")
    @KafkaListener(topicPartitions =
            {
//                    @TopicPartition(topic = "exam-test3", partitions = {"0"}),
                    @TopicPartition(topic = "topic20", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))
            },
            groupId = "my-group", properties = {"auto.offset.reset:earliest"})
    public void consume(String message) throws IOException {
        log.info("========== Consumed message getEmail : {}", message);
    }
}

