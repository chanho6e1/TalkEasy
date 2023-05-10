package com.talkeasy.server.service.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Objects;

@Service
@Slf4j
public class RestTemplateService {
    @Value("${spring.flask.server}")
    private String flaskServer;

    public Object[] requestDayAnalysis(String id) throws ParseException, IOException {

        RestTemplate restTemplate = new RestTemplate();
        // Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // Body set
        URI url = UriComponentsBuilder
                .fromUriString(flaskServer)
                .path("/location")
                .queryParam("id", id)
                .encode(Charset.defaultCharset())
                .build()
                .toUri();
        HttpEntity<?> requestMessage = new HttpEntity<>("", httpHeaders);

        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(url, Object[].class);

        log.info("========== responseEntity.getBody(). : {}", Objects.requireNonNull(responseEntity.getBody()).length);
        ObjectMapper objectMapper = new ObjectMapper();

        for (Object o : responseEntity.getBody()) {
            String jsonSting = objectMapper.writeValueAsString(o);
            log.info("========== jsonSting : {}", jsonSting);
        }
        return responseEntity.getBody();
    }
}
