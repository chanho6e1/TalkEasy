package com.talkeasy.server.service.aac;

import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.common.exception.ArgumentMismatchException;
import com.talkeasy.server.domain.aac.AAC;
import com.talkeasy.server.domain.aac.AACCategory;
import com.talkeasy.server.domain.aac.CustomAAC;
import com.talkeasy.server.dto.aac.CustomAACDto;
import com.talkeasy.server.dto.aac.ResponseAACDto;
import com.talkeasy.server.dto.chat.ChatTextDto;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AACService {

    private final MongoTemplate mongoTemplate;

    @Value("${openAI.api.key}")
    private String apiKey;

    // 카테고리 종류
    public PagedResponse<AACCategory> getCategory() {

        List<AACCategory> result = mongoTemplate.findAll(AACCategory.class, "aac_category");

        return new PagedResponse<>(result, 1);
    }

    // 카테고리별 aac 조회
    public PagedResponse<?> getAacByCategory(String userId, String categoryId, int fixed, int offset, int size) {

        if(categoryId.equals("9")){
            return getAacByCustom(userId, offset, size);
        }

        Pageable pageable = PageRequest.of(offset - 1, size, Sort.by(Sort.Direction.ASC, "title")); // 가나다 순으로
        Query query = new Query(Criteria.where("category").is(categoryId).and("fixed").is(fixed)).with(pageable);

        List<AAC> filteredMetaData = mongoTemplate.find(query, AAC.class);

        Page<AAC> metaDataPage = PageableExecutionUtils.getPage(
                filteredMetaData,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1), AAC.class)
        );

        List<ResponseAACDto> result = metaDataPage.getContent().stream().map((a)-> new ResponseAACDto(a)).collect(Collectors.toList());
        return new PagedResponse<>(result, metaDataPage.getTotalPages());
    }

    public PagedResponse<CustomAAC> getAacByCustom(String userId, int offset, int size) {

        Pageable pageable = PageRequest.of(offset - 1, size, Sort.by(Sort.Direction.ASC, "text")); // 가나다 순으로
        Query query = new Query(Criteria.where("userId").is(userId)).with(pageable);

        List<CustomAAC> filteredMetaData = mongoTemplate.find(query, CustomAAC.class);

        Page<CustomAAC> metaDataPage = PageableExecutionUtils.getPage(
                filteredMetaData,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1), CustomAAC.class)
        );

        List<ResponseAACDto> result = metaDataPage.getContent().stream().map((a)-> new ResponseAACDto(a)).collect(Collectors.toList());
        return new PagedResponse<>(result, metaDataPage.getTotalPages());
    }

    // aac 연관 동사 조회
    public PagedResponse<AAC> getRelativeVerb(String aacId) {

        Query query = new Query(Criteria.where("id").is(aacId));
        AAC aac = mongoTemplate.findOne(query, AAC.class);

        List<ResponseAACDto> result = Arrays.stream(aac.getRelative_verb().split(" "))
                .filter(a -> !a.equals("0"))
                .map(a -> mongoTemplate.findOne(new Query(Criteria.where("id").is(a)), AAC.class))
                .map(a-> new ResponseAACDto(a))
                .collect(Collectors.toList());

        return new PagedResponse<>(result, 1);
    }


/* 커스텀 AAC */
    public String deleteCustomAac(String aacId,  String userId) {

        Query query = new Query(Criteria.where("id").is(aacId));
        CustomAAC customAAC = mongoTemplate.findOne(query, CustomAAC.class);

        isMine(userId, customAAC.getUserId());

        mongoTemplate.remove(customAAC);
        return aacId;
    }

    public String postCustomAac(CustomAACDto customAac, String userId) {

        CustomAAC customAAC = mongoTemplate.insert(CustomAAC.builder().userId(userId).text(customAac.getTitle()).build());
        return customAAC.getId();

    }

    public String putCustomAac(String aacId, CustomAACDto customAac, String userId) {

//        System.out.println(String.valueOf(aacId));
        log.info("{}",aacId);
        Query query = new Query(Criteria.where("id").is(aacId));
        CustomAAC customAAC = mongoTemplate.findOne(query, CustomAAC.class);

        System.out.println(customAAC.getText() + " " + customAAC.getUserId());

        isMine(userId, customAAC.getUserId());

        customAAC.setText(customAac.getTitle());
        mongoTemplate.save(customAAC);

        return customAAC.getId();
    }

    public void isMine(String userId, String aacUserId) {

        if(!userId.equals(aacUserId)){
            throw new ArgumentMismatchException("본인이 작성한 aac가 아닙니다");
        }

    }

    /* gpt */
    public String getGenereteText(ChatTextDto text) {

        OpenAiService service = new OpenAiService(apiKey);

//        String inputText = "'" + text.getText() + " . this words rearrange and complete in korean please.'";
        String inputText = "'" + text.getText() + " . 이 단어들을 어순 맞게 문장 완성해줘.'";

        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(inputText)
                .model("text-davinci-003")
                .maxTokens(100) // 원하는 출력 길이 조정 (선택사항)
                .temperature(0.5) // 다양성 조절 (선택사항)
                .n(1)
                .build();

        return service.createCompletion(completionRequest).getChoices().get(0).getText().strip();
    }

}
