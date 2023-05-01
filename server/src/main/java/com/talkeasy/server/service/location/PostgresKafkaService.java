package com.talkeasy.server.service.location;

import com.talkeasy.server.domain.Location;
import com.talkeasy.server.dto.LocationDto;
import com.talkeasy.server.repository.location.PostgresKafkaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostgresKafkaService {

    private final PostgresKafkaRepository postgresKafkaRepository;

    public void bulk(List<LocationDto> list) {
        log.info("========== postgresKafkaService bulk 함수 list size : {}", list.size());
        List<Location> locations = new ArrayList<>();
        for (LocationDto locationDto : list) {
            locations.add(locationDto.toEntity());
        }

        bulkDo(locations);
    }

    @Transactional
    public void bulkDo(List<Location> list) {

        postgresKafkaRepository.saveAll(list);
    }

}
