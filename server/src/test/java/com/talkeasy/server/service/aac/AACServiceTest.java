package com.talkeasy.server.service.aac;

import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.domain.aac.AAC;
import com.talkeasy.server.domain.aac.AACCategory;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.dto.aac.ResponseAACDto;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AACServiceTest {

    @InjectMocks
    private AACService aacService;
    @Mock
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("[GET] Category_List")
    void getCategory() {

        AACCategory aacCategory1 = AACCategory.builder().id("1").title("고정").build();
        AACCategory aacCategory2 = AACCategory.builder().id("2").title("음식").build();

        List<AACCategory> fakedataList = List.of(aacCategory1, aacCategory2);

        Mockito.when(mongoTemplate.findAll(AACCategory.class, "aac_category")).thenReturn(fakedataList);

        PagedResponse<AACCategory> result = aacService.getCategory();

        PagedResponse<AACCategory> expectedResponse = new PagedResponse<>(HttpStatus.OK, fakedataList, 1);

        // 결과 비교
        Assertions.assertEquals(expectedResponse.getStatus(), result.getStatus());
        Assertions.assertEquals(expectedResponse.getData(), result.getData());
        Assertions.assertEquals(expectedResponse.getTotalPages(), result.getTotalPages());

        // mongoTemplate의 findAll 메서드가 호출되었는지 확인
        Mockito.verify(mongoTemplate, Mockito.times(1)).findAll(AACCategory.class, "aac_category");
    }

    @Test
    @DisplayName("[GET] AAC_By_Category")
    void getAacByCategory() {

        OAuth2UserImpl member = new OAuth2UserImpl(Member.builder().id("1").build());
        String categoryId = "1";
        int fixed = 0;
        int offset = 1;
        int size = 10;

        AAC aac1 = AAC.builder().id("1").title("안녕하세요").category("1").build();
        AAC aac2 = AAC.builder().id("2").title("안녕히계세요").category("1").build();

        List<AAC> aacList = List.of(aac1, aac2);

        Pageable pageable = PageRequest.of(offset - 1, size, Sort.by(Sort.Direction.ASC, "title")); // 가나다 순으로
        Query query = new Query(Criteria.where("category").is(categoryId).and("fixed").is(fixed)).with(pageable);
        Mockito.when(mongoTemplate.find(query, AAC.class)).thenReturn(aacList);

        PagedResponse<?> testResult = aacService.getAacByCategory(member.getId(), categoryId, fixed, offset, size);

        // 결과 비교
        List<ResponseAACDto> result = (List<ResponseAACDto>) testResult.getData();

        assertEquals(200, testResult.getStatus());
        assertEquals(1, testResult.getTotalPages());
        assertEquals(2, result.size());

        // mongoTemplate의 findAll 메서드가 호출되었는지 확인
        Mockito.verify(mongoTemplate, Mockito.times(1)).find(query, AAC.class);

    }

    @Test
    void getAacByCustom() {
    }

    @Test
    void getRelativeVerb() {
    }

    @Test
    void deleteCustomAac() {
    }

    @Test
    void postCustomAac() {
    }

    @Test
    void putCustomAac() {
    }

    @Test
    void isMine() {
    }

    @Test
    void getGenereteText() {
    }
}