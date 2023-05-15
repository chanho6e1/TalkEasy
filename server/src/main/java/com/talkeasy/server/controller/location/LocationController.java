package com.talkeasy.server.controller.location;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.dto.location.LocationDto;
import com.talkeasy.server.service.location.KafkaConsumerService;
import com.talkeasy.server.service.location.KafkaProducerService;
import com.talkeasy.server.service.location.LocationService;
import com.talkeasy.server.service.location.RestTemplateService;
import com.talkeasy.server.service.member.MemberService;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;


@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"location 컨트롤러"})
public class LocationController {

    private final KafkaProducerService kafkaProducerService;
    private final KafkaConsumerService kafkaConsumerService;
    private final RestTemplateService restTemplateService;
    private final LocationService locationService;
    private final MemberService memberService;

    @PostMapping
    @ApiOperation(value = "위치정보", notes = "위치정보를 받아와서 카프카에 저장")
    public ResponseEntity<CommonResponse<Object>> saveLocationToKafka(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl member, @RequestBody LocationDto locationDto) {

        locationDto.setUserId(member.getId());
        locationDto.setName(member.getName());
        kafkaProducerService.sendMessage(locationDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, ""));
    }

    @PostMapping("/consumer")
    @ApiOperation(value = "위치정보", notes = "위치정보를 받아와서 카프카에 저장")
    public ResponseEntity<CommonResponse<Object>> testConsume() {

        kafkaConsumerService.consumeLocationEvent();

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, ""));
    }

    @GetMapping("/day")
    @ApiOperation(value = "당일 위치 분석 테스트", notes = "당일 이동 정보(좌표, 시간) 리턴")
    public ResponseEntity<CommonResponse<Object>> dayAnalysisTest(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl member) {

        kafkaConsumerService.consumeLocationEvent();

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                HttpStatus.OK, locationService.getLocationOfDay(member.getId())));
    }

    @GetMapping("/week")
    @ApiOperation(value = "일주일 위치 분석 테스트", notes = "일주일 동안 많이 갔던 장소 순위 리턴")
    public ResponseEntity<CommonResponse<Object>> weekAnalysisTest() {

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                HttpStatus.OK, restTemplateService.requestDayAnalysis()));
    }

    @GetMapping("/analysis/{protegeId}")
    @ApiOperation(value = "위치 분석", notes = "오늘, 일주일 위치 분석\n피보호자가 위치정보 열람을 허용하지 않았다면 statusCode 210을 리턴")
    public ResponseEntity<CommonResponse<Object>> analysis(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl protector, @PathVariable String protegeId) {

        kafkaConsumerService.consumeLocationEvent();
        System.out.println("보호자 ID : " + protector.getId() + " 피보호자 ID : " + protector);
        if (!memberService.checkFollow(protector.getId(), protegeId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CommonResponse.of(
                    HttpStatus.NO_CONTENT, ""));
        }

        HashMap<String, Object> analysis = new HashMap<>();
        analysis.put("day", locationService.getLocationOfDay(protegeId));
        analysis.put("week", locationService.getLocationOfWeek(protegeId));

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                HttpStatus.OK, analysis));
    }

}
