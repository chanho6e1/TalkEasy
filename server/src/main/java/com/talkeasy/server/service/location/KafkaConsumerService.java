package com.talkeasy.server.service.location;

import com.talkeasy.server.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
    private final PostgresKafkaService postgresKafkaService;
    private final ConsumerFactory<String, LocationDto> consumerFactory;

    public LocalDateTime convertTimestampToLocalDateTime(long timeStamp) {

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault());
    }

    public void consumeLocationEvent() {
        try (KafkaConsumer<String, LocationDto> kafkaConsumer = new KafkaConsumer<>(consumerFactory.getConfigurationProperties())) {

            String topicName = "location-event";
            kafkaConsumer.subscribe(Collections.singletonList(topicName));

            List<LocationDto> locationDtos = new ArrayList<>();

            ConsumerRecords<String, LocationDto> records = kafkaConsumer.poll(Duration.ofSeconds(10));
            log.info("==================== record count : {}", records.count());
            for (ConsumerRecord<String, LocationDto> record : records) {
                if (record.value() != null) {
                    LocationDto locationDto = record.value();
                    locationDto.setDateTime(convertTimestampToLocalDateTime(record.timestamp()));
                    locationDtos.add(record.value());
                    log.info("Topic : {}, Partition : {}, Offset : {}, Timestamp : {}", record.topic(), record.partition(), record.offset(), convertTimestampToLocalDateTime(record.timestamp()));
                }
            }
            try {
                if (!locationDtos.isEmpty()) {
                    postgresKafkaService.bulk(locationDtos);
                }
            } catch (Exception e) {
                log.error("error : {}", e.getMessage());
            }

        } catch (Exception e) {
            log.error("error : {}", e.getMessage());
        }
    }

    // 카프카 리스너
    @KafkaListener(topics = "location-event", groupId = "group-test", containerFactory = "kafkaListenerContainerFactory", properties = {"auto.offset.reset:earliest"})
    public void consumeLocationEvent(ConsumerRecord<String, LocationDto> record) {
        log.info("========== [Consumed message] value : {}, topic : {}, partition : {}, offset : {}, timestamp : {}, key : {}", record.value().toString(), record.topic(), record.partition(), record.offset(), convertTimestampToLocalDateTime(record.timestamp()), record.key());
    }

}


