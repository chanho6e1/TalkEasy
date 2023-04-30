package com.talkeasy.server.service.location;

import com.talkeasy.server.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
    private final PostgresKafkaService postgresKafkaService;
    private final ConsumerFactory<String, LocationDto> consumerFactory;

    public String getTimestampToDate(long timeStamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault());

        return dateTime.format(formatter);
    }

    public void bulk() {
        try (KafkaConsumer<String, LocationDto> kafkaConsumer = new KafkaConsumer<>(consumerFactory.getConfigurationProperties())) {
            org.apache.kafka.common.TopicPartition topicPartition = new TopicPartition("topic-test-02", 0);

            kafkaConsumer.assign(Collections.singletonList(topicPartition));
            kafkaConsumer.seek(topicPartition, 0);

            List<Map<String, LocationDto>> list;
            Map<String, LocationDto> map;

            list = new ArrayList<>();
            map = new HashMap<>();
            ConsumerRecords<String, LocationDto> records = kafkaConsumer.poll(Duration.ofSeconds(10));

            log.info("==================== record count : {}", records.count());

            for (ConsumerRecord<String, LocationDto> record : records) {
                if (record.value() != null) {
                    map.put(String.valueOf(record.value().hashCode()), record.value());
                    log.info("Topic : {}, Partition : {}, Offset : {}, Timestamp : {}, Key : {}", record.topic(), record.partition(), record.offset(), getTimestampToDate(record.timestamp()), record.key());
                }
                list.add(map);
            }
            try {
                if (!list.get(0).isEmpty()) {
                    // TODO: BULK TO POSTGRES
                    log.info("========== bulk to postgres");
                    postgresKafkaService.bulk(list);
                }
            } catch (Exception e) {
                log.error("error : {}", e.getMessage());
            }

        } catch (Exception e) {
            log.error("error : {}", e.getMessage());
        }
    }

    // 카프카 리스너
//    @KafkaListener(topicPartitions = {@TopicPartition(topic = "topic-test-02", partitions = {"0"})},groupId = "my-group", properties = {"auto.offset.reset:earliest"})
//    @KafkaListener(topicPartitions = {@TopicPartition(topic = "topic-test-02", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))
//    }, groupId = "my-group", properties = {"auto.offset.reset:earliest"})
//public void consume(ConsumerRecord<String, LocationDto> record) {
//        log.info("========== [Consumed message] value : {}, topic : {}, partition : {}, offset : {}, timestamp : {}, key : {}", record.value().toString(), record.topic(), record.partition(),record.offset(), getTimestampToDate(record.timestamp()), record.key());
//    }

}

