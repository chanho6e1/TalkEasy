package com.talkeasy.server.service.location;

import com.talkeasy.server.dto.LocationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PostgresKafkaService {

    public void bulk(List<Map<String, LocationDto>> list) {
        log.info("========== postgresKafkaService bulk 함수 list size : {}", list.size());
        for (int i = 0; i < list.size(); i++) {
            log.info("========== {} : values {}", i, list.get(i).values());
        }

    }
}
