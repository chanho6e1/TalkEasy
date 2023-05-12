package com.talkeasy.server.controller.alarm;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
@Api(tags = {"alarm 컨트롤러"})
public class AlarmController {
}
