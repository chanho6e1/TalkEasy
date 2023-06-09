package com.talkeasy.server.controller.member;


import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.config.s3.S3Uploader;
import com.talkeasy.server.dto.user.MemberDetailResponse;
import com.talkeasy.server.dto.user.MemberInfoUpdateRequest;
import com.talkeasy.server.service.member.MemberService;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@Api(tags = {"Member 관련 API"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final S3Uploader s3Uploader;


    @ApiOperation(value = "회원정보 수정하기", notes = "사진 변경, 이름 변경, 성별 변경, 생년월일 변경")
    @PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> saveImg(@ApiParam(value = "사용자 등록 이미지") @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile,
                                                  @RequestPart(name = "value") MemberInfoUpdateRequest memberInfoUpdateRequest,
                                                  @ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User) throws IOException {
        log.info("========== /update multipartFile : {}, memberInfoUpdateRequest : {}, member : {}", multipartFile, memberInfoUpdateRequest.getName(), oAuth2User.getMember().getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, new MemberDetailResponse(memberService.updateUserInfo(multipartFile, memberInfoUpdateRequest, oAuth2User.getMember().getId()))));
    }

    @ApiOperation(value = "유저 정보 조회", notes = "유저 정보 조회하기")
    @GetMapping
    public ResponseEntity<CommonResponse> getUserInfo(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User) throws IOException {

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
                HttpStatus.OK, new MemberDetailResponse(oAuth2User.getMember())));
    }

    @ApiOperation(value = "유저 탈퇴", notes = "유저 아이디 입력 시, 관련된 정보 삭제")
    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteUserInfo(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User) throws IOException {

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(
//                HttpStatus.NO_CONTENT, memberService.deleteUserInfo("64613b09971240357edbb88f")));
                HttpStatus.NO_CONTENT, memberService.deleteUserInfo(oAuth2User.getId())));
    }

    @ApiOperation(value = "위치정보 제공 일괄 동의/비동의", notes = "api 요청 시, 토글 처리")
    @PutMapping(value = "/locations")
    public ResponseEntity<CommonResponse> putLocationStatus(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, memberService.putLocationStatus(oAuth2User.getId())));
    }

}
