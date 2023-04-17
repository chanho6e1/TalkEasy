package com.talkeasy.server.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

@Document("test")
@Getter
@Setter
public class Test {
    private String name;
    private String job;
}
