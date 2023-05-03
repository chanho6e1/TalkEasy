package com.talkeasy.server.controller;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Api(tags = {"test 컨트롤러"})
public class TestController {

    private final TestService testService;

    @GetMapping()
    @ApiOperation(value = "어떤 컨트롤러인지", notes = "자세한 설명(어떤 값을 입력하고 어떤 값을 반환하는지)")
    public ResponseEntity<CommonResponse> makeChatRoom() {
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                "test 조회 성공", testService.getTests()));
    }


    private final Environment env;

    @GetMapping("/profile")
    public String getProfile() {
//        System.out.println(env.getActiveProfiles());
        return Arrays.stream(env.getActiveProfiles())
                .findFirst()
                .orElse("");


    }

}
