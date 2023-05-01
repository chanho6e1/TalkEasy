package com.talkeasy.server.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Location {

    // TODO : Geometry로 변경
    @Column
    String email;
    @Column
    String x;
    @Column
    String y;
    @Id
    private Long id;

}
