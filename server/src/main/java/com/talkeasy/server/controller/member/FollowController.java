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
@RequestMapping("/api/follow")
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

}
