package com.talkeasy.server.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import org.springframework.data.annotation.Id;

@Document("test1")
@Getter
@Setter
public class Test {

    @Id
    private Integer id;
    private String title;
    private Integer category;
    private Integer is_noun;
}
