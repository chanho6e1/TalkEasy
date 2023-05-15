package com.talkeasy.server.dto.location;

import com.talkeasy.server.domain.location.Location;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    String userId;
    String name;
    Point point;
    Double lat;
    Double lon;
    LocalDateTime dateTime;

    public Location toEntity() {
        return Location.builder().userId(userId).name(name).geom(point).dateTime(dateTime).build();
    }
}
