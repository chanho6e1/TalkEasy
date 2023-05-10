package com.talkeasy.server.dto.location;

import com.talkeasy.server.domain.location.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto implements Serializable {
    String userId;
    String name;
    String x;
    String y;
    LocalDateTime dateTime;

    public Location toEntity(){
        return Location.builder().userId(userId).name(name).x(x).y(y).dateTime(dateTime).build();
    }
}