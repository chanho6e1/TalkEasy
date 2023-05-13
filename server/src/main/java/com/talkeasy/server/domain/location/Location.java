package com.talkeasy.server.domain.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import org.locationtech.jts.geom.Point;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@SequenceGenerator(name = "LOCATION_GENERATOR", sequenceName = "MY_SEQUENCES", allocationSize = 1)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCATION_GENERATOR")
    private Long id;
    @Column
    private String userId;
    @Column
    private String name;
    @Column(columnDefinition = "geometry(Point, 4326)")
    @JsonIgnoreProperties(value = "envelope")
    private Point geom;
    @Column
    private LocalDateTime dateTime;

}
