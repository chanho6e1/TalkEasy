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
    String email;
    String x, y;
    LocalDateTime dateTime;

    public Location toEntity(){
        return Location.builder().email(email).x(x).y(y).dateTime(dateTime).build();
    }
}
