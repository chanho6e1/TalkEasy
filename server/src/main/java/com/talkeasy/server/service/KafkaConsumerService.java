package com.talkeasy.server.service;

import com.talkeasy.server.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(topicPartitions = {@TopicPartition(topic = "topic-test-01", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))
    }, groupId = "my-group")
    public void consume(LocationDto message) {
        log.info("========== [Consumed message] : {}", message.toString());
    }
}

