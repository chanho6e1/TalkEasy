package com.talkeasy.server.service.location;


import com.talkeasy.server.dto.location.LocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@EnableScheduling
@EnableAsync
@Service
@RequiredArgsConstructor
@Slf4j
public class Scheduler {

    private final KafkaConsumerService kafkaConsumerService;
    private final KafkaProducerService kafkaProducerService;

    @Scheduled(cron = "0 3 0/12 * * *", zone = "Asia/Seoul") // 00:03, 12:03 에 postgresQL에 데이터 저장
    public void locationEvent() {

        log.info("========== scheduler 실행 time : {}", LocalDateTime.now());
        kafkaConsumerService.consumeLocationEvent();
    }

    @Scheduled(cron = "0 0/30 * * * *", zone = "Asia/Seoul") // dummy data 생성
    public void makeLocation() {

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        double lon = 128.3445734;
        double lat = 36.119485;

        lon += random.nextDouble();
        lat += random.nextDouble();

        LocationDto locationDto = new LocationDto();
        locationDto.setUserId("64613b09971240357edbb88f");
        locationDto.setName("강은선인데이름이왕왕길어용괜찮아욥");
        locationDto.setLon(String.valueOf(lon));
        locationDto.setLat(String.valueOf(lat));

        kafkaProducerService.sendMessage(locationDto);
    }

}
