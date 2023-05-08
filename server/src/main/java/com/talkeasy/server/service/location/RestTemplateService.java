package com.talkeasy.server.service.location;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import com.talkeasy.server.domain.location.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class RestTemplateService {
    @Value("http://k8d207.p.ssafy.io:5000")
//    @Value("${spring.kafka.bootstrap-servers}")
    private String flaskServer;

    public Object[] requestDayAnalysis(String email) throws ParseException {

        RestTemplate restTemplate = new RestTemplate();
        // Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // Body set
        URI url = UriComponentsBuilder
                .fromUriString(flaskServer)
                .path("/location")
                .queryParam("email", email)
                .encode(Charset.defaultCharset())
                .build()
                .toUri();
        HttpEntity<?> requestMessage = new HttpEntity<>("", httpHeaders);



        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(url, Object[].class);
        log.info("========== responseEntity.getBody() : {}", responseEntity.getBody());


        return responseEntity.getBody();
    }
}
