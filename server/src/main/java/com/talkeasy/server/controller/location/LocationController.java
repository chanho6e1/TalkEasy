package com.talkeasy.server.controller.location;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.dto.LocationDto;
import com.talkeasy.server.dto.MessageDto;
import com.talkeasy.server.service.KafkaProducerService;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"location 컨트롤러"})
public class LocationController {

    private final KafkaProducerService kafkaProducerService;

    @PostMapping
    @ApiOperation(value = "위치정보", notes = "위치정보를 받아와서 카프카에 저장")
    public ResponseEntity<CommonResponse> saveKafka(@AuthenticationPrincipal OAuth2UserImpl member, @RequestBody LocationDto locationDto) {

        locationDto.setEmail(member.getEmail());
        kafkaProducerService.sendMessage(member.getId() + ", " + "좌표data");

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                "카프카에 저장 성공"));
    }
}
