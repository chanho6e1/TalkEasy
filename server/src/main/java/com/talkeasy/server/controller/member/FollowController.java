package com.talkeasy.server.controller.member;

import com.talkeasy.server.service.member.FollowService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Follow API"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/follow")
@Slf4j
public class FollowController {


    private final FollowService followService;

//    @ApiOperation(value = "팔로우", notes = "팔로우 한다")
//    @PostMapping("/{toUserId}")
//    public ResponseEntity<CommonResponse> follow(@ApiIgnore @CurrentUser UserPrincipal user, @PathVariable("toUserId") Long toUserId) throws Exception {
//        return ResponseEntity.ok().body(CommonResponse.of(
//                HttpStatus.CREATED, "팔로우 성공", followService.follow(user.getId(), toUserId)));
//    }
//

//    @ApiOperation(value = "언팔로우", notes = "언팔로우 한다")
//    @DeleteMapping("/{toUserId}")
//    public ResponseEntity<CommonResponse> unfollow(@ApiIgnore @CurrentUser UserPrincipal user, @PathVariable("toUserId") Long toUserId) throws Exception {
//        return ResponseEntity.ok().body(CommonResponse.of(
//                HttpStatus.NO_CONTENT, "언팔로우 성공", followService.deleteByFollowingIdAndFollowerId(user.getId(), toUserId)));
//    }

}
