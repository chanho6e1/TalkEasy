package com.talkeasy.server.common.controller;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Api(tags = {"test 컨트롤러"})
public class TestController {

    private final TestService testService;

    @GetMapping()
    @ApiOperation(value = "test", notes = "test")
    public ResponseEntity<CommonResponse> test() {

        return ResponseEntity.ok().body(CommonResponse.of(
                "test 조회 성공", testService.getTests()));
    }
}
