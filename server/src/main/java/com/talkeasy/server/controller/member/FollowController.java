package com.talkeasy.server.controller.member;

import com.talkeasy.server.common.CommonResponse;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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


    // 주보호자 등록/해제 토글
    // 보호자 이미 등록되어 있으면 새로 들어온 아이디로 갈아끼우기
    @ApiOperation(value = "주보호자 등록", notes = "주보호자로 등록할 사용자의 아이디를 입력 시, 이미 등록된 보호자가 있으면 새로 입력된 아이디로 변경 \n" +
            "기존에 등록된 주보호자의 아이디 입력 시, 주보호자 해제")
    @PutMapping("/protector/{targetId}")
    public ResponseEntity<CommonResponse> putProtector(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User, @PathVariable String targetId){
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of("주보호자 등록/변경 성공", followService.putProtector("64475bb2970b4a6441e96c50", targetId)));
    }

    // 위치정보 토글
    @ApiOperation(value = "위치정보 허용 변경", notes = "피보호자가 각각의 보호자에 대해 위치 정보 접근 여부에 대해 설정한다. 처음 초기 설정은 false")
    @PutMapping("/location/{targetId}")
    public ResponseEntity<CommonResponse> putLocationStatus(@ApiIgnore @AuthenticationPrincipal OAuth2UserImpl oAuth2User, @PathVariable String targetId){
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of("위치정보 접근권한 변경 성공", followService.putLocationStatus("64475bb2970b4a6441e96c50", targetId)));
    }
}
