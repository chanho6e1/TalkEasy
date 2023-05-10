package com.talkeasy.server.service.location;

import com.talkeasy.server.domain.location.Location;
import com.talkeasy.server.dto.location.LocationDto;
import com.talkeasy.server.repository.location.LocationRepository;
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
public class LocationService {

    private final LocationRepository locationRepository;

    @Transactional
    public void bulk(List<LocationDto> list) {
        log.info("========== locationRepository bulk 함수 list size : {}", list.size());
        List<Location> locations = new ArrayList<>();
        for (LocationDto locationDto : list) {
            locations.add(locationDto.toEntity());
        }
        locationRepository.saveAll(locations);
    }

}
