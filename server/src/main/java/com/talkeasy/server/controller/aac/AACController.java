package com.talkeasy.server.controller.aac;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.dto.aac.CustomAACDto;
import com.talkeasy.server.dto.chat.ChatTextDto;
import com.talkeasy.server.service.aac.AACService;
import com.talkeasy.server.service.chat.TTSService;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

@RestController
@RequestMapping("/api/aac")
@RequiredArgsConstructor
@Api(tags = {"AAC 컨트롤러"})
public class AACController {

    private final AACService aacService;
    private final TTSService ttsService;

    @GetMapping("/categories")
    @ApiOperation(value = "카테고리 목록 조회", notes = "카테고리 목록을 조회한다.")
    public ResponseEntity<?> getCategory() {

        return ResponseEntity.status(HttpStatus.OK).body(aacService.getCategory());
    }

    @GetMapping("/categories/{categoryId}")
    @ApiOperation(value = "카테고리별 aac 조회", notes = "1: 고정 2: 음식 3: 인간관계 4: 기분 5: 긴급/피해상황  6: 병원/건강  7: 생활  8: 교통  9:사용자 지정 \n" +
            "fixedList : 고정 리스트,  aacList : 일반 리스트\n" +
            "페이지네이션 제거")
    public ResponseEntity<?> getCategoryContents(@PathVariable String categoryId,
                                                 @ApiIgnore @AuthenticationPrincipal OAuth2UserImpl member) {

        if (categoryId.equals("9"))
            return ResponseEntity.status(HttpStatus.OK).body(aacService.getAacByCustom(member.getId()));
        return ResponseEntity.status(HttpStatus.OK).body(aacService.getAacByCategory(categoryId));
    }

    @GetMapping("/relative-verb/{aacId}")
    @ApiOperation(value = "aac 연관 동사 조회", notes = "aac별 연관 동사 조회")
    public ResponseEntity<?> getRelativeVerb(@PathVariable String aacId) {

        return ResponseEntity.status(HttpStatus.OK).body(aacService.getRelativeVerb(aacId));
    }


    /////////////사용자 지정

    @PostMapping("/custom")
    @ApiOperation(value = "사용자 지정 aac 추가", notes = "사용자가 저장할 text를 주면 생성된 aacId를 반환")
    public ResponseEntity<?> postCustomAac(@RequestBody CustomAACDto customAac,
                                           @ApiIgnore @AuthenticationPrincipal OAuth2UserImpl member){

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, aacService.postCustomAac(customAac, member.getId())));
    }

    @PutMapping("/custom/{aac-id}")
    @ApiOperation(value = "사용자 지정 aac 수정", notes = "수정할 accId를 주면 수정된 accId를 반환")
    public ResponseEntity<?> putCustomAac(@PathVariable(value = "aac-id") String aacId,
                                          @RequestBody CustomAACDto customAac,
                                          @ApiIgnore @AuthenticationPrincipal OAuth2UserImpl member) {

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, aacService.putCustomAac(aacId, customAac, member.getId())));
    }

    @DeleteMapping("/custom/{aac-id}")
    @ApiOperation(value = "사용자 지정 aac 삭제", notes = "삭제할 accId를 주면 삭제된 accId를 반환")
    public ResponseEntity<?> deleteCustomAac(@PathVariable(value = "aac-id") String aacId,
                                             @ApiIgnore @AuthenticationPrincipal OAuth2UserImpl member) {

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, aacService.deleteCustomAac(aacId, member.getId())));
    }

    ////////////텍스트 관련

    @PostMapping("/generate")
    @ApiOperation(value = "완전한 텍스트 생성", notes = "텍스트를 주면 완전한 텍스트를 반환") //'아파요 고통스러워요 배 땀 생리대'  어순에 맞게 배열해줘, 사족 붙이지 말고
    public ResponseEntity<?> getGenereteText(@RequestBody ChatTextDto text) {

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, aacService.getGenereteText(text)));
    }


    @PostMapping("/tts")
    @ApiOperation(value = "text-to-speech", notes = "text를 주면 음성 파일로 반환")
    public ResponseEntity<CommonResponse> getTTS(@RequestBody ChatTextDto text, @ApiIgnore @AuthenticationPrincipal OAuth2UserImpl member) throws IOException, UnsupportedAudioFileException {
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of(
                HttpStatus.CREATED, ttsService.getTTS(text.getText())));

    }

}
