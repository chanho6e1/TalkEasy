package com.talkeasy.server.controller.member;


import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.domain.Member;
import com.talkeasy.server.dto.user.MemberInfoUpdateRequest;
import com.talkeasy.server.service.member.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = {"Member 관련 API"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "회원정보 수정하기", notes = "사진 변경, 이름 변경, 성별 변경, 생년월일 변경")
    @PostMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> saveImg(@ApiParam(value = "사용자 등록 이미지") @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile
            , @RequestPart(name = "value") MemberInfoUpdateRequest memberInfoUpdateRequest,
                                                  @AuthenticationPrincipal Member member) throws IOException {
        log.info("========== /update multipartFile : {}, memberInfoUpdateRequest : {}, member : {}", multipartFile, memberInfoUpdateRequest.getName(), member.getName());

//        memberService.update(member);

        return ResponseEntity.ok().body(CommonResponse.of("회원가입 성공"));
    }

    @ApiOperation(value = "test", notes = "test")
    @GetMapping
    public ResponseEntity<CommonResponse> test(@AuthenticationPrincipal Member member) throws IOException {
        log.info("========== test member : {}", member.getName());

        return ResponseEntity.ok().body(CommonResponse.of("member filter test"));
    }

}
