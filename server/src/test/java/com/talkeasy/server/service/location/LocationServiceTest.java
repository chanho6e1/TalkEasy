package com.talkeasy.server.service.location;

import com.talkeasy.server.domain.location.Location;
import com.talkeasy.server.dto.location.LocationDto;
import com.talkeasy.server.dto.location.LocationResponseDto;
import com.talkeasy.server.repository.location.LocationRepository;
import com.talkeasy.server.repository.location.ReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("위치 서비스")
class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;
    @Mock
    private ReportRepository reportRepository;
    @InjectMocks
    private LocationService locationService;

    @Test
    @DisplayName("마지막 위치 조회")
    void getLastOne() {
        // given
        String userId = "6458d8a17660191f96dfe179";
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = geometryFactory.createPoint(new Coordinate(33.132462, 135.443626));
        LocalDateTime date = LocalDateTime.of(2023, 5, 12, 9, 41, 38, 399);
        Location location = Location.builder().id(228L).dateTime(date).name("jiwon").userId(userId).geom(point).build();
        given(locationRepository.findTopByUserIdOrderByDateTimeDesc(userId)).willReturn(location);

        // when
        Location result = locationService.getLastOne(userId);

        // then
        then(locationRepository).should().findTopByUserIdOrderByDateTimeDesc(userId);
        assertThat(result).isEqualTo(location);
    }

    @Test
    @DisplayName("오늘 위치 조회")
    void getLocationOfDay() {
        // given
        String userId = "6458d8a17660191f96dfe179";

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTimeOfToday = now.withHour(0).withMinute(0).withSecond(0).withNano(0);

        LocalDateTime date1 = LocalDateTime.of(2023, 5, 16, 13, 19, 6, 68);

        LocationDto locationDto1 = LocationDto.builder()
                .lat(138.443626)
                .lon(33.132462)
                .dateTime(date1)
                .build();
        LocationDto locationDto2 = LocationDto.builder()
                .lat(132.443626)
                .lon(33.132462)
                .dateTime(date1)
                .build();

        // when
        List<LocationResponseDto> result = locationService.getLocationOfDay(userId);

    }


}
