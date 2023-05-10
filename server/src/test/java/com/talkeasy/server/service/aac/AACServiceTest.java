package com.talkeasy.server.service.aac;

import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.common.exception.ArgumentMismatchException;
import com.talkeasy.server.common.exception.ResourceNotFoundException;
import com.talkeasy.server.domain.aac.AAC;
import com.talkeasy.server.domain.aac.AACCategory;
import com.talkeasy.server.domain.aac.CustomAAC;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.dto.aac.CustomAACDto;
import com.talkeasy.server.dto.aac.ResponseAACDto;
import com.talkeasy.server.dto.aac.ResponseAACListDto;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AACServiceTest {

    @InjectMocks
    private AACService aacService;
    @Mock
    private MongoTemplate mongoTemplate;
    @Value("${openAI.api.key}")
    private String apiKey;

    @Test
    @DisplayName("[GET] 카테고리 목록")
    void getCategory() {

        AACCategory aacCategory1 = AACCategory.builder().id("1").title("고정").build();
        AACCategory aacCategory2 = AACCategory.builder().id("2").title("음식").build();

        List<AACCategory> fakedataList = List.of(aacCategory1, aacCategory2);

        when(mongoTemplate.findAll(AACCategory.class, "aac_category")).thenReturn(fakedataList);

        PagedResponse<AACCategory> result = aacService.getCategory();

        PagedResponse<AACCategory> expectedResponse = new PagedResponse<>(HttpStatus.OK, fakedataList, 1);

        // 결과 비교
        Assertions.assertEquals(expectedResponse.getStatus(), result.getStatus());
        Assertions.assertEquals(expectedResponse.getData(), result.getData());
        Assertions.assertEquals(expectedResponse.getTotalPages(), result.getTotalPages());

        // mongoTemplate의 findAll 메서드가 호출되었는지 확인
        verify(mongoTemplate, Mockito.times(1)).findAll(AACCategory.class, "aac_category");
    }

    @Test
    @DisplayName("[GET] 카테고리별 AAC")
    void getAacByCategory() {

        OAuth2UserImpl member = new OAuth2UserImpl(Member.builder().id("1").build());
        String categoryId = "1"; // categoryId = 9일 때 다르게 처리

        int offset = 1;
        int size = 10;

        AAC aac1 = AAC.builder().id("1").title("안녕하세요").category("1").build();
        AAC aac2 = AAC.builder().id("2").title("안녕히계세요").category("1").build();

        when(mongoTemplate.find(any(Query.class), eq(AAC.class))).thenReturn(List.of(aac1, aac2));

        PagedResponse<?> testResult = aacService.getAacByCategory(categoryId, offset, size);

        // 결과 비교
        ResponseAACListDto result = (ResponseAACListDto) testResult.getData();

        assertEquals(200, testResult.getStatus());
        assertEquals(1, testResult.getTotalPages());
        assertEquals(2, result.getFixedList().size());
        assertEquals(2, result.getAacList().size());

        // mongoTemplate의 findAll 메서드가 호출되었는지 확인
        verify(mongoTemplate, Mockito.times(2)).find(any(Query.class), eq(AAC.class));

    }

    @Test
    @DisplayName("[GET] 커스텀 AAC")
    void getAacByCustom() {
        OAuth2UserImpl member = new OAuth2UserImpl(Member.builder().id("6447cb89dade8b8f866e8f34").build());
        int offset = 1;
        int size = 10;

        CustomAAC aac1 = CustomAAC.builder().id("6448c3e09ce4637f2c4269e6").text("헉").userId("6447cb89dade8b8f866e8f34").build();
        CustomAAC aac2 = CustomAAC.builder().id("6449bcad36204f5f1b7770d1").text("히히").userId("6447cb89dade8b8f866e8f34").build();

        List<CustomAAC> filteredMetaData = List.of(aac1, aac2);

        when(mongoTemplate.find(any(Query.class), eq(CustomAAC.class))).thenReturn(filteredMetaData);

        PagedResponse<ResponseAACListDto> testResult = aacService.getAacByCustom(member.getId(), offset, size);

        // 결과 비교
        ResponseAACListDto result = (ResponseAACListDto) testResult.getData();

        assertEquals(200, testResult.getStatus());
        assertEquals(1, testResult.getTotalPages());
        assertEquals(2, result.getAacList().size());
        assertEquals(0, result.getFixedList().size());

        // mongoTemplate의 find 메서드가 호출되었는지 확인
        verify(mongoTemplate, Mockito.times(1)).find(any(Query.class), eq(CustomAAC.class));
    }

    @Test
    @DisplayName("[GET] 커스텀 aac 없을 경우")
    void getAacByCustomNoCustom() {
        OAuth2UserImpl member = new OAuth2UserImpl(Member.builder().id("6447cb89dade8b8f866e8f34").build());
        int offset = 1;
        int size = 10;

        when(mongoTemplate.find(any(Query.class), eq(CustomAAC.class))).thenReturn(Collections.emptyList());

        PagedResponse<ResponseAACListDto> testResult = aacService.getAacByCustom(member.getId(), offset, size);

        // 결과 비교
        ResponseAACListDto result = (ResponseAACListDto) testResult.getData();

        assertThat(testResult).isNotNull();

        assertEquals(200, testResult.getStatus());
        assertEquals(0, testResult.getTotalPages());
        assertEquals(0, result.getAacList().size());
        assertEquals(0, result.getFixedList().size());

        // mongoTemplate의 find 메서드가 호출되었는지 확인
        verify(mongoTemplate, Mockito.times(1)).find(any(Query.class), eq(CustomAAC.class));
    }


    @Test
    @DisplayName("[GET] 연관동사")
    void getRelativeVerb() {
        String aacId = "22";

        AAC aac = AAC.builder().id("22").title("돈까스").relative_verb("112 113").build();

        AAC aac1 = AAC.builder().id("112").title("매워요").build();
        AAC aac2 = AAC.builder().id("113").title("짜요").build();

        when(mongoTemplate.findOne(Mockito.any(Query.class), Mockito.eq(AAC.class))).thenReturn(aac, aac1, aac2);

        PagedResponse<ResponseAACDto> result = aacService.getRelativeVerb(aacId);
        List<ResponseAACDto> responseAACList = (List<ResponseAACDto>) result.getData();

        // 결과 검증
        assertEquals(2, responseAACList.size());
        assertEquals("매워요", responseAACList.get(0).getTitle());
        assertEquals("짜요", responseAACList.get(1).getTitle());

        verify(mongoTemplate, Mockito.times(3)).findOne(Mockito.any(Query.class), Mockito.eq(AAC.class));
    }

    @Test
    @DisplayName("[POST] CustomAAC :: 유저가 존재할 때")
    void postCustomAacSuccess() {

        CustomAACDto customAACDto = new CustomAACDto();
        customAACDto.setTitle("흑흑");
        String userId = "6447cb89dade8b8f866e8f34";

        Member member = Member.builder().id(userId).build();
        when(mongoTemplate.findOne(Query.query(Criteria.where("id").is(userId)), Member.class)).thenReturn(member);

        assertEquals(userId, member.getId());

        CustomAAC result = CustomAAC.builder().id("1").text("흑흑").userId(userId).build();

        when(mongoTemplate.insert(any(CustomAAC.class))).thenReturn(result);

        String testResult = aacService.postCustomAac(customAACDto, member.getId());
        assertEquals("1", testResult);

        verify(mongoTemplate, times(1)).findOne(any(Query.class), ArgumentMatchers.eq(Member.class));
        verify(mongoTemplate, times(1)).insert(any(CustomAAC.class));

    }

    @Test
    @DisplayName("[POST] CustomAAC :: user가 존재하지 않을 경우")
    void postCustomAacNoUser() {

        CustomAACDto customAACDto = new CustomAACDto();
        customAACDto.setTitle("흑흑");
        String userId = "6447cb89dade8b8f866e8f34";

        when(mongoTemplate.findOne(any(Query.class), ArgumentMatchers.eq(Member.class))).thenReturn(null);
        assertThatThrownBy(() -> aacService.postCustomAac(customAACDto, userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("정보를 찾을 수 없습니다");

        verify(mongoTemplate, times(1)).findOne(any(Query.class), ArgumentMatchers.eq(Member.class));
    }

    @Test
    @DisplayName("[DELETE] 커스텀 AAC 삭제 성공")
    void deleteCustomAac_Success() {

        String aacId = "1";
        String userId = "6447cb89dade8b8f866e8f34";
        String text = "아무래도";

        CustomAAC customAAC = CustomAAC.builder().id(aacId).userId(userId).text(text).build();

        Query query = new Query(Criteria.where("id").is(aacId));
        when(mongoTemplate.findOne(query, CustomAAC.class)).thenReturn(customAAC);

        aacService.deleteCustomAac(aacId, userId);

        verify(mongoTemplate, times(1)).findOne(any(Query.class), ArgumentMatchers.eq(CustomAAC.class));
        verify(mongoTemplate, times(1)).remove(any(CustomAAC.class));

    }

    @Test
    @DisplayName("[DELETE] 커스텀 AAC 삭제 :: user와 일치하지 않을 경우")
    void deleteCustomAac_NoUser() {

        String aacId = "1";
        String userId = "6447cb89dade8b8f866e8f34";
        String text = "아무래도";

        CustomAAC customAAC = CustomAAC.builder().id(aacId).userId("anotherUserId").text(text).build();

        Query query = new Query(Criteria.where("id").is(aacId));
        when(mongoTemplate.findOne(query, CustomAAC.class)).thenReturn(customAAC);

        assertThrows(ArgumentMismatchException.class, () -> aacService.deleteCustomAac(aacId, userId));

        verify(mongoTemplate, times(1)).findOne(any(Query.class), ArgumentMatchers.eq(CustomAAC.class));
        verify(mongoTemplate, times(0)).remove(any(CustomAAC.class));

    }

    @Test
    @DisplayName("[PUT] CustomAAC :: 수정 성공")
    void putCustomAac_Success() {

        String aacId = "1";
        String userId = "6447cb89dade8b8f866e8f34";
        String text = "아무래도";

        CustomAAC customAAC = CustomAAC.builder().id(aacId).userId(userId).text(text).build();

        when(mongoTemplate.findOne(any(Query.class), ArgumentMatchers.eq(CustomAAC.class))).thenReturn(customAAC);

        CustomAACDto customAACDto = new CustomAACDto();
        customAACDto.setTitle("아무래도2");

        String result = aacService.putCustomAac(aacId, customAACDto, userId);
        assertEquals(aacId, result);

        verify(mongoTemplate, times(1)).findOne(any(Query.class), ArgumentMatchers.eq(CustomAAC.class));
        verify(mongoTemplate, times(1)).save(any(CustomAAC.class));

    }

    @Test
    @DisplayName("[PUT] CustomAAC :: 수정 실패")
    void putCustomAac_Mismatch_User() {

        String aacId = "1";
        String userId = "anotherUserId";

        CustomAAC customAAC = CustomAAC.builder().id(aacId).userId("6447cb89dade8b8f866e8f34").text("아무래도").build();

        when(mongoTemplate.findOne(any(Query.class), ArgumentMatchers.eq(CustomAAC.class))).thenReturn(customAAC);

        CustomAACDto customAACDto = new CustomAACDto();
        customAACDto.setTitle("아무래도2");

        assertThrows(ArgumentMismatchException.class, () -> aacService.putCustomAac(aacId, customAACDto, userId));

        verify(mongoTemplate, times(1)).findOne(any(Query.class), ArgumentMatchers.eq(CustomAAC.class));
        verify(mongoTemplate, times(0)).save(any(CustomAAC.class));

    }

    @Test
    @DisplayName("IsMine :: 아이디 같을 경우")
    public void testIsMine_UserIdEqualsAacUserId() {

        String userId = "testUserId";
        String aacUserId = "testUserId";

        assertDoesNotThrow(() -> aacService.isMine(userId, aacUserId));
    }

    @Test
    @DisplayName("IsMine :: 아이디 다를 경우")
    public void testIsMine_UserIdNotEqualsAacUserId() {

        String userId = "testUserId";
        String aacUserId = "differentUserId";

        assertThrows(ArgumentMismatchException.class, () -> aacService.isMine(userId, aacUserId));
    }

    @Test
    @DisplayName("[GET] 문장생성")
    @Disabled
    void getGenereteText() {

//        OpenAiService service = new OpenAiService(apiKey);
//
//        ChatTextDto input = new ChatTextDto();
//        input.setText("Input text");
//
//        String inputText = "'" + input.getText() + " .' 이 단어들을 어순 맞게 문장 완성해줘.";
//
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .prompt(inputText)
//                .model("text-davinci-003")
//                .maxTokens(100) // 원하는 출력 길이 조정 (선택사항)
//                .temperature(0.5) // 다양성 조절 (선택사항)
//                .n(1)
//                .build();
//
//        String result = service.createCompletion(completionRequest).getChoices().get(0).getText().strip().replace("\"", "");

    }

}