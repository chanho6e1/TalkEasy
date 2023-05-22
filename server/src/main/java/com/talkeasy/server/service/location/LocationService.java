package com.talkeasy.server.service.location;

import com.talkeasy.server.domain.location.Location;
import com.talkeasy.server.dto.location.LocationDto;
import com.talkeasy.server.dto.location.LocationResponseDto;
import com.talkeasy.server.repository.location.LocationRepository;
import com.talkeasy.server.repository.location.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;
    private final ReportRepository reportRepository;

    @Transactional
    public void bulk(List<LocationDto> list) {

        log.info("========== locationRepository bulk 함수 list size : {}", list.size());
        List<Location> locations = new ArrayList<>();
        for (LocationDto locationDto : list) {
            locations.add(locationDto.toEntity());
        }
        locationRepository.saveAll(locations);
    }

    public Location getLastOne(String userId) {

        return locationRepository.findTopByUserIdOrderByDateTimeDesc(userId);
    }

    public List<LocationResponseDto> getLocationOfDay(String userId) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTimeOfToday = now.withHour(0).withMinute(0).withSecond(0).withNano(0);

        return locationRepository.findByUserIdAndDateTimeAfter(userId, startTimeOfToday).stream().map(LocationResponseDto::new).collect(Collectors.toList());
    }

    public List<LocationResponseDto> getLocationOfWeek(String userId) {

        return reportRepository.findByUserId(userId).stream().map(LocationResponseDto::new).collect(Collectors.toList());
    }

}
