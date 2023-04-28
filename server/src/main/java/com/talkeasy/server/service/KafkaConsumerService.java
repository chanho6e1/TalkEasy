package com.talkeasy.server.service;

import com.talkeasy.server.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private static String getTimestampToDate(long timeStamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault());

        return dateTime.format(formatter);
    }

    @KafkaListener(topicPartitions = {@TopicPartition(topic = "topic-test-01", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))
    }, groupId = "my-group")
    public void consume(ConsumerRecord<String, LocationDto> record) {

        log.info("========== [Consumed message] timestamp : {}", getTimestampToDate(record.timestamp()));
        log.info("========== [Consumed message] LocationDto : {}", record.value().toString());
    }

}

