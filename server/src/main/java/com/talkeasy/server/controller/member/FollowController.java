package com.talkeasy.server.controller.member;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.dto.user.FollowRequestDto;
import com.talkeasy.server.service.member.FollowService;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@Api(tags = {"Follow 컨트롤러"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/follows")
@Slf4j
public class FollowController {


    private final FollowService followService;

    @ApiOperation(value = "팔로우", notes = "팔로우 한다")
    @PostMapping("/{toUserId}")
    public ResponseEntity<CommonResponse> follow(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User,
                                                 @PathVariable("toUserId") String toUserId,
                                                 @RequestBody FollowRequestDto followRequestDto) throws IOException {

//        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
//                HttpStatus.CREATED, followService.follow("645a0420c5b2c82e3afaf9e4", "6459dfcf393c266aa80f5710", followRequestDto)));
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, followService.follow(oAuth2User.getId(), toUserId, followRequestDto)));

    }

    @ApiOperation(value = "친구 상세정보", notes = "followId를 주면 친구 상세조회 가능")
    @GetMapping("/{followId}")
    public ResponseEntity<CommonResponse> getUserInfoByFollow (@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User,
                                                 @PathVariable("followId") String followId) throws IOException {

//        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
//                HttpStatus.CREATED, followService.getUserInfoByFollow("6459dfcf393c266aa80f5710", "645b7a87019c7f5131d179b0")));

//                HttpStatus.CREATED, followService.getUserInfoByFollow("64613b09971240357edbb88f", "64617a36596a5a20f5a76007")));
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, followService.getUserInfoByFollow(oAuth2User.getId(), followId)));

    }

    @ApiOperation(value = "특이사항 수정", notes = "followId를 주면 특이사항 수정 가능")
    @PutMapping("/{followId}")
    public ResponseEntity<CommonResponse> putMemo(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User,
                                                 @PathVariable("followId") String followId,
                                                 @RequestBody FollowRequestDto followRequestDto) throws IOException {

//        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
//                HttpStatus.CREATED, followService.putMemo("645a0420c5b2c82e3afaf9e4", "645b7a87019c7f5131d179af", followRequestDto)));
 return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, followService.putMemo(oAuth2User.getId(), followId, followRequestDto)));

    }


    @ApiOperation(value = "언팔로우", notes = "언팔로우 한다")
    @DeleteMapping("/{toUserId}")
    public ResponseEntity<CommonResponse> unfollow(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User,
                                                   @PathVariable("toUserId") String toUserId) throws IOException {
        return ResponseEntity.ok().body(CommonResponse.of(
                HttpStatus.OK, followService.deleteByFollow(oAuth2User.getId(), toUserId)));
//   return ResponseEntity.ok().body(CommonResponse.of(
//                HttpStatus.OK, followService.deleteByFollow("645a0420c5b2c82e3afaf9e4", "6459dfcf393c266aa80f5710")));


    }

    @ApiOperation(value = "나의 팔로워 목록을 조회한다", notes = "나의 팔로우 목록을 조회한다")
    @GetMapping()
    public ResponseEntity<PagedResponse> getfollow(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User) {
        return ResponseEntity.ok().body((
                followService.getfollow(oAuth2User.getId())));
//        return ResponseEntity.ok().body((
//                followService.getfollow("64613b09971240357edbb88f")));
    }

    @ApiOperation(value = "피보호자가 보호자 별명 수정", notes = "피보호자가 보호자의 별명 설정\n" +
            "보호자는 피보호자의 별명 설정 불가 :: status:400과 동시에 data: \"보호자는 피보호자의 별명을 설정할 수 없습니다.\" 출력\n" +
            "== RequestParam으로 nickName 전달!!!! 초기 별명은 \"\" <- 빈 문자열로 저장 ==")
    @PutMapping("/{followId}/nick-name")
    public ResponseEntity<CommonResponse> putNickName(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User,
                                                  @PathVariable("followId") String followId,
                                                  @RequestParam String nickName) throws IOException {

//        Member member = Member.builder().id("6459dfcf393c266aa80f5710").role(1).build();
//        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
//                HttpStatus.CREATED, followService.putNickName(member, "645b7a87019c7f5131d179b0", "토르토르")));
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, followService.putNickName(oAuth2User.getMember(), followId, nickName)));

    }

    // 주보호자 등록/해제 토글
    // 보호자 이미 등록되어 있으면 새로 들어온 아이디로 갈아끼우기
    @ApiOperation(value = "주보호자 등록", notes = "주보호자로 등록할 사용자의 아이디를 입력 시, 이미 등록된 보호자가 있으면 새로 입력된 아이디로 변경 \n" +
            "기존에 등록된 주보호자의 아이디 입력 시, 주보호자 해제")
    @PutMapping("/protector/{followId}")
    public ResponseEntity<CommonResponse> putProtector(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User, @PathVariable String followId) {
//        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(HttpStatus.OK, followService.putProtector("645a0420c5b2c82e3afaf9e4", "645b7a87019c7f5131d179af")));
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(HttpStatus.OK, followService.putProtector(oAuth2User.getId(), followId)));
    }

    // 위치정보 토글
    @ApiOperation(value = "위치정보 허용 변경", notes = "피보호자가 각각의 보호자에 대해 위치 정보 접근 여부에 대해 설정한다. 처음 초기 설정은 false")
    @PutMapping("/location/{followId}")
    public ResponseEntity<CommonResponse> putLocationStatus(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User, @PathVariable String followId) {
//        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(HttpStatus.OK, followService.putLocationStatus("645a0420c5b2c82e3afaf9e4", "645b7a87019c7f5131d179af")));
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(HttpStatus.OK, followService.putLocationStatus(oAuth2User.getId(), followId)));
    }
}
