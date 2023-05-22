package com.talkeasy.server.service.location;

import com.talkeasy.server.domain.location.Location;
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
import static org.mockito.ArgumentMatchers.any;
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
        String userId = "user1";
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = geometryFactory.createPoint(new Coordinate(37.1234, 127.1234));
        LocalDateTime date = LocalDateTime.of(2023, 5, 12, 9, 41, 38, 399);
        Location location = Location.builder().id(1L).dateTime(date).name("user1").userId(userId).geom(point).build();
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
        String userId = "user1";

        LocalDateTime date1 = LocalDateTime.of(2023, 5, 16, 13, 14, 15, 16);
        LocalDateTime date2 = LocalDateTime.of(2023, 5, 16, 13, 16, 17, 18);

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        double x1 = 37.1234;
        double y1 = 127.1234;
        double x2 = 38.1234;
        double y2 = 128.1234;

        Point point1 = geometryFactory.createPoint(new Coordinate(x1, y1));
        Point point2 = geometryFactory.createPoint(new Coordinate(x2, y2));

        LocationResponseDto locationDto1 = LocationResponseDto.builder()
                .lon(x1)
                .lat(y1)
                .dateTime(date1)
                .build();
        LocationResponseDto locationDto2 = LocationResponseDto.builder()
                .lon(x2)
                .lat(y2)
                .dateTime(date2)
                .build();

        Location location1 = Location.builder()
                .dateTime(date1)
                .geom(point1)
                .build();
        Location location2 = Location.builder()
                .dateTime(date2)
                .geom(point2)
                .build();
        given(locationRepository.findByUserIdAndDateTimeAfter(any(), any())).willReturn(List.of(location1, location2));

        // when
        List<LocationResponseDto> result = locationService.getLocationOfDay(userId);

        // then
        then(locationRepository).should().findByUserIdAndDateTimeAfter(any(), any());

        assertThat(result.get(0).getLat()).isEqualTo(locationDto1.getLat());
        assertThat(result.get(0).getLon()).isEqualTo(locationDto1.getLon());
        assertThat(result.get(0).getDateTime()).isEqualTo(locationDto1.getDateTime());

        assertThat(result.get(1).getLat()).isEqualTo(locationDto2.getLat());
        assertThat(result.get(1).getLon()).isEqualTo(locationDto2.getLon());
        assertThat(result.get(1).getDateTime()).isEqualTo(locationDto2.getDateTime());
    }

}
