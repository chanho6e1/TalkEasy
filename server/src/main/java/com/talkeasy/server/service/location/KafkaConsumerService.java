package com.talkeasy.server.service.location;

import com.talkeasy.server.domain.location.Location;
import com.talkeasy.server.dto.location.LocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
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
    private final LocationService locationService;
    private final ConsumerFactory<String, LocationDto> consumerFactory;


    public void consumeLocationEvent() {
        try (KafkaConsumer<String, LocationDto> kafkaConsumer = new KafkaConsumer<>(consumerFactory.getConfigurationProperties())) {

            String topicName = "location-event";
            kafkaConsumer.subscribe(Collections.singletonList(topicName));

            List<LocationDto> locationDtos = new ArrayList<>();

            ConsumerRecords<String, LocationDto> records = kafkaConsumer.poll(Duration.ofSeconds(3));
            log.info("==================== record count : {}", records.count());

            for (ConsumerRecord<String, LocationDto> record : records) {
                LocationDto locationDto = record.value();
                Location last = locationService.getLastOne(locationDto.getUserId());

                convertLocationToPoint(locationDto);

                if (last != null && isCloseArea(last.getGeom(), locationDto.getPoint())) {
                    log.info("========== isClose, no data insert");
                    continue;
                }

                convertTimestampToLocalDateTime(locationDto, record.timestamp());
                locationDtos.add(locationDto);

                log.info("Topic : {}, Partition : {}, Offset : {}, Timestamp : {}", record.topic(), record.partition(), record.offset(), locationDto.getDateTime());
            }

            if (!locationDtos.isEmpty()) {
                locationService.bulk(locationDtos);
            }

        } catch (Exception e) {
            log.error("error : {}", e.getMessage());
        }
    }

    private void convertLocationToPoint(LocationDto locationDto) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        locationDto.setPoint(geometryFactory.createPoint(new Coordinate(Double.parseDouble(locationDto.getLat()), Double.parseDouble(locationDto.getLon()))));
    }

    public void convertTimestampToLocalDateTime(LocationDto locationDto, long timeStamp) {
        locationDto.setDateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault()));
    }

    private boolean isCloseArea(Point a, Point b) {
        return a.distance(b) < 0.5;
    }

    // 카프카 리스너
//    @KafkaListener(topics = "location-event", groupId = "group-test", containerFactory = "kafkaListenerContainerFactory", properties = {"auto.offset.reset:earliest"})
    public void consumeLocationEvent(ConsumerRecord<String, LocationDto> record) {
        log.info("========== [Consumed message] value : {}, topic : {}, partition : {}, offset : {}, timestamp : {}, key : {}", record.value().toString(), record.topic(), record.partition(), record.offset(), record.timestamp(), record.key());
    }

}


