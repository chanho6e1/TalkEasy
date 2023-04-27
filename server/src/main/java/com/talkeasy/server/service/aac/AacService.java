package com.talkeasy.server.service.aac;

import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.common.exception.ArgumentMismatchException;
import com.talkeasy.server.domain.aac.AAC;
import com.talkeasy.server.domain.aac.AacCategory;
import com.talkeasy.server.domain.aac.CustomAAC;
import com.talkeasy.server.dto.aac.CustomAACDto;
import com.talkeasy.server.dto.aac.ResponseAACDto;
import com.talkeasy.server.dto.chat.ChatTextDto;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.edit.EditChoice;
import com.theokanning.openai.edit.EditRequest;
import com.theokanning.openai.edit.EditResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AacService {

    private final MongoTemplate mongoTemplate;

    // 고정 카테고리 내용
    public PagedResponse<AAC> getFixedAac(String categoryId) {

        Query query = new Query(Criteria.where("category").is(categoryId));
        List<AAC> result = mongoTemplate.find(query, AAC.class);

        return new PagedResponse<>(result.stream().map((a)-> new ResponseAACDto(a)).collect(Collectors.toList()), 1);
    }

    // 카테고리 종류
    public PagedResponse<AacCategory> getCategory() {

        List<AacCategory> result = mongoTemplate.findAll(AacCategory.class, "aac_category");

        return new PagedResponse<>(result, 1);
    }

    // 카테고리별 aac 조회
    public PagedResponse<?> getAacByCategory(String userId, String categoryId, int offset, int size) {

        if(categoryId.equals("9")){
            return getAacByCustom(userId, offset, size);
        }

        Pageable pageable = PageRequest.of(offset - 1, size, Sort.by(Sort.Direction.ASC, "title")); // 가나다 순으로
        Query query = new Query(Criteria.where("category").is(categoryId)).with(pageable);

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
        }else{
            return;
        }

    }

    /* gpt */
    public String getGenereteText(ChatTextDto text) {

        OpenAiService service = new OpenAiService("{my-api-key");

        String input = "아파요 고통스러워요 배 땀 생리대";

//        EditRequest editRequest = EditRequest.builder()
//                .input(input)
//                .model("text-davinci-edit-001")
//                .instruction("어순에 맞게 배열하고 문장 만들어줘")
//                .build();
//
//        EditResult editResult = service.createEdit(editRequest);
//        System.out.println(editResult.getChoices().get(0).getText());

        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(text.getText())
                .model("text-davinci-003")
                .maxTokens(100) // 원하는 출력 길이 조정 (선택사항)
                .temperature(0.5) // 다양성 조절 (선택사항)
//                .echo(true)
                .build();

//        System.out.println(service.createCompletion(completionRequest).getChoices());

        return service.createCompletion(completionRequest).getChoices().get(0).toString();

//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth("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"); // apiKey는 OpenAI에서 발급받은 API 키입니다.
//
//        String requestBody = "{\"prompt\": \"'" + text + "'어순에 맞게 배열해줘, 사족 붙이지 말고\"}";
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
//        String url = "https://api.openai.com/v1/engines/davinci-codex/completions";
//
//        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
//        if (response.getStatusCode() == HttpStatus.OK) {
//            String responseBody = response.getBody();
//            // API로부터 응답을 받아 처리합니다.
//            System.out.println(responseBody);
//            return responseBody;
//        } else {
//            // API 호출이 실패한 경우 에러 처리합니다.
//            System.err.println("Failed to call GPT API: " + response.getStatusCodeValue());
//            return "Fail";
//        }

//        OpenAiService service = new OpenAiService("{gpt api 키}");
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .prompt("Somebody once told me the world is gonna roll me")
//                .model("ada")
//                .echo(true)
//                .build();

//        return "";
    }

}
