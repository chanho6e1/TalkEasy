package com.talkeasy.server.dto.location;

import com.talkeasy.server.domain.location.Location;
import com.talkeasy.server.domain.location.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LocationResponseDto {

    Double lat;
    Double lon;
    LocalDateTime dateTime;

    public LocationResponseDto(Location location) {

        this.lat = location.getGeom().getCoordinate().getY();
        this.lon = location.getGeom().getCoordinate().getX();
        this.dateTime = location.getDateTime();
    }
    public LocationResponseDto(Report report) {

        this.lat = report.getLat();
        this.lon = report.getLon();
        this.dateTime = report.getDatetime();
    }

}
