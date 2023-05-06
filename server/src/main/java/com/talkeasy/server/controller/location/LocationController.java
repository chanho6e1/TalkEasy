package com.talkeasy.server.controller.location;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.dto.LocationDto;
import com.talkeasy.server.service.location.KafkaConsumerService;
import com.talkeasy.server.service.location.KafkaProducerService;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"location 컨트롤러"})
public class LocationController {

    private final KafkaProducerService kafkaProducerService;
    private final KafkaConsumerService kafkaConsumerService;

    @PostMapping
    @ApiOperation(value = "위치정보", notes = "위치정보를 받아와서 카프카에 저장")
    public ResponseEntity<CommonResponse<Object>> saveLocationToKafka(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl member, @RequestBody LocationDto locationDto) {

        locationDto.setEmail(member.getEmail());
        kafkaProducerService.sendMessage(locationDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED,""));
    }

    @PostMapping("/consumer")
    @ApiOperation(value = "위치정보", notes = "위치정보를 받아와서 카프카에 저장")
    public ResponseEntity<CommonResponse<Object>> testConsume() {

        kafkaConsumerService.consumeLocationEvent();

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED,""));
    }
}
