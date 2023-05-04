package com.talkeasy.server.service.member;

import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.common.exception.ArgumentMismatchException;
import com.talkeasy.server.common.exception.ResourceAlreadyExistsException;
import com.talkeasy.server.common.exception.ResourceNotFoundException;
import com.talkeasy.server.domain.member.Follow;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.dto.user.FollowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final MongoTemplate mongoTemplate;

    public String follow(String myId, String toUserId) {

        followDetail(myId, toUserId);
        followDetail(toUserId, myId);

        return "팔로우 성공";
    }

    public void followDetail(String myId, String toUserId) {

        Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("id").is(myId)), Member.class)).orElseThrow(() -> new ResourceNotFoundException("member", "userId", myId));

        Optional followOptional = Optional.ofNullable( mongoTemplate.findOne(Query.query(Criteria.where("fromUserId").is(myId).and("toUserId").is(toUserId)), Follow.class));

        if (followOptional.isPresent()) {
            throw new ResourceAlreadyExistsException("이미 팔로우되어 있습니다");
        }

        Follow toFollow = Follow.builder().fromUserId(myId).toUserId(toUserId).memo(null).MainStatus(false).locationStatus(false).build();

        mongoTemplate.insert(toFollow);

    }


    public String deleteByFollow(String myId, String toUserId) {

        deleteByFollowDetail(myId, toUserId);
        deleteByFollowDetail(toUserId, myId);

        return "언팔로우 성공";
    }
    public void deleteByFollowDetail(String myId, String toUserId) {

        Follow user = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("fromUserId").is(myId).and("toUserId").is(toUserId)), Follow.class)).orElseThrow(() -> new ResourceNotFoundException("이미 언팔로우 상태입니다"));

        mongoTemplate.remove(user);

    }

    public PagedResponse<?> getfollow(String userId) {
        /* userId -> id로 바꿔야함*/

        Query query = new Query(Criteria.where("fromUserId").is(userId));

        List<Follow> filteredMetaData = Optional.ofNullable(mongoTemplate.find(query, Follow.class)).orElseThrow(
                ()-> new ResourceNotFoundException("친구 목록이 비어있습니다.")
        );
        
        List<FollowResponse> result = filteredMetaData.stream()
                .map((follow) ->
                        new FollowResponse(mongoTemplate.findOne(Query.query(Criteria.where("id").is(follow.getToUserId())), Member.class), follow))
                .collect(Collectors.toList());

        Collections.sort(result, Comparator.comparing(FollowResponse::getUserName));

        return new PagedResponse<>(result,1);
    }

    /* 주보호자 등록 */
    public String putProtector(String userId, String targetId) {

        Follow targetUser = getTargetUser(userId, targetId);

        Follow mainProtector = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("MainStatus").is(true)
                .and("fromUserId").is(userId)), Follow.class)).orElse(null);

        if (mainProtector != null) { // 기존에 등록된 주 보호자가 있을 경우, 해제
            mainProtector.setMainStatus(false);
            mongoTemplate.save(mainProtector);

            if (mainProtector.getToUserId().equals(targetUser.getToUserId())) // 기존에 등록된 보호자와 동일할 경우
                return targetId;
        }

        // 새로운 주보호자 등록
        targetUser.setMainStatus(true);
        mongoTemplate.save(targetUser);

        return targetId;
    }

    /* 위치정보 접근권한 설정 */
    public boolean putLocationStatus(String userId, String targetId) {
        Follow targetUser = getTargetUser(userId, targetId);

        targetUser.setLocationStatus(!targetUser.getLocationStatus());
        mongoTemplate.save(targetUser);

        return targetUser.getLocationStatus();
    }

    /* 피보호자와 친구설정되어있는 보호자 정보 조회 */
    private Follow getTargetUser(String userId, String targetId) {
        return Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("toUserId").is(targetId)
                .and("fromUserId").is(userId)), Follow.class)).orElseThrow(() -> new ResourceNotFoundException("친구목록에 존재하지 않는 사용자 입니다."));
    }
}
