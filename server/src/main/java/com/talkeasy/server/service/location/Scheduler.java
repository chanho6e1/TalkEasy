package com.talkeasy.server.service.location;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@EnableScheduling
@EnableAsync
@Service
@RequiredArgsConstructor
@Slf4j
public class Scheduler {

    private final KafkaConsumerService kafkaConsumerService;

    //    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul") // 초, 분, 시, 일, 월, 주, (년)
    @Scheduled(cron = "0 3 0 * * *", zone = "Asia/Seoul") // 매일 새벽 5분에 postgresQL에 데이터를 넣는 스케쥴러
    public void autoUpdate() {
        log.info("========== scheduler 실행 time : {}", LocalDateTime.now());
        kafkaConsumerService.consume();
    }

}
