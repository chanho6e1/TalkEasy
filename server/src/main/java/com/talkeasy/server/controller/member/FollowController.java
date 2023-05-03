package com.talkeasy.server.controller.member;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.domain.member.Member;
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

@Api(tags = {"Follow 컨트롤러"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/follows")
@Slf4j
public class FollowController {


    private final FollowService followService;

    @ApiOperation(value = "팔로우", notes = "팔로우 한다")
    @PostMapping("/{toUserId}")
    public ResponseEntity<?> follow(@ApiIgnore @AuthenticationPrincipal Member oAuth2User,
                                    @PathVariable("toUserId") String toUserId,
                                    @RequestParam String userId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                "팔로우 성공", followService.follow(userId, toUserId)));
//        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
//                 "팔로우 성공", followService.follow(oAuth2User.getId(), toUserId)));
    }


    @ApiOperation(value = "언팔로우", notes = "언팔로우 한다")
    @DeleteMapping("/{toUserId}")
    public ResponseEntity<CommonResponse> unfollow(@ApiIgnore @AuthenticationPrincipal Member oAuth2User,
                                                   @PathVariable("toUserId") String toUserId,
                                                   @RequestParam String userId) {
        return ResponseEntity.ok().body(CommonResponse.of(
                followService.deleteByFollowingIdAndFollowerId(userId, toUserId)));
//        return ResponseEntity.ok().body(CommonResponse.of(
//                 followService.deleteByFollowingIdAndFollowerId(oAuth2User.getId(), toUserId)));
    }

    @ApiOperation(value = "나의 팔로워 목록을 조회한다", notes = "나의 팔로우 목록을 조회한다")
    @GetMapping()
    public ResponseEntity<?> getfollow(@ApiIgnore @AuthenticationPrincipal Member oAuth2User,
                                       @RequestParam String userId,
                                       @RequestParam(required = false, defaultValue = "1") int offset,
                                       @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
//        return ResponseEntity.ok().body(CommonResponse.of(
//                followService.getfollow(oAuth2User.getId(), offset, size)));
        return ResponseEntity.ok().body(followService.getfollow(userId, offset, size));
    }


    // 주보호자 등록/해제 토글
    // 보호자 이미 등록되어 있으면 새로 들어온 아이디로 갈아끼우기
    @ApiOperation(value = "주보호자 등록", notes = "주보호자로 등록할 사용자의 아이디를 입력 시, 이미 등록된 보호자가 있으면 새로 입력된 아이디로 변경 \n" +
            "기존에 등록된 주보호자의 아이디 입력 시, 주보호자 해제")
    @PutMapping("/protector/{targetId}")
    public ResponseEntity<CommonResponse> putProtector(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User, @PathVariable String targetId){
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of("주보호자 등록/변경 성공", followService.putProtector(oAuth2User.getId(), targetId)));
//        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of("주보호자 등록/변경 성공", followService.putProtector("64475bb2970b4a6441e96c50", targetId)));
    }

    // 위치정보 토글
    @ApiOperation(value = "위치정보 허용 변경", notes = "피보호자가 각각의 보호자에 대해 위치 정보 접근 여부에 대해 설정한다. 처음 초기 설정은 false")
    @PutMapping("/location/{targetId}")
    public ResponseEntity<CommonResponse> putLocationStatus(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User, @PathVariable String targetId){
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of("위치정보 접근권한 변경 성공", followService.putLocationStatus(oAuth2User.getId(), targetId)));
//        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of("위치정보 접근권한 변경 성공", followService.putLocationStatus("64475bb2970b4a6441e96c50", targetId)));
    }
}
