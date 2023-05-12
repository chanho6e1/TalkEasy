package com.talkeasy.server.dto.location;

import com.talkeasy.server.domain.location.Location;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LocationResponseDto {

    Double lat;
    Double lon;
    LocalDateTime dateTime;

    public LocationResponseDto(Location location) {
        this.lat = location.getGeom().getCoordinate().getY();
        this.lon = location.getGeom().getCoordinate().getX();
        this.dateTime = location.getDateTime();
    }
}
