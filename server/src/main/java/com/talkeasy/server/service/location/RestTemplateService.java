package com.talkeasy.server.service.location;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;

@Service
@Slf4j
public class RestTemplateService {
    @Value("${spring.flask.server}")
    private String flaskServer;

    public Object[] requestDayAnalysis() {

        RestTemplate restTemplate = new RestTemplate();
        // Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // Body set
        URI url = UriComponentsBuilder
                .fromUriString(flaskServer)
                .path("/location")
                .encode(Charset.defaultCharset())
                .build()
                .toUri();

        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(url, Object[].class);

        return responseEntity.getBody();
    }
}
