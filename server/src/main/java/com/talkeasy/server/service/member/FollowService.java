package com.talkeasy.server.service.member;

import com.talkeasy.server.common.exception.ResourceNotFoundException;
import com.talkeasy.server.domain.member.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final MongoTemplate mongoTemplate;

    /* 주보호자 등록 */
    public String putProtector(String userId, String targetId) {

        Follow targetUser = getTargetUser(userId, targetId);

        Follow mainProtector = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("MainStatus").is(true)
                .and("fromUserId").is(userId)), Follow.class)).orElse(null);

        if (mainProtector != null){ // 기존에 등록된 주 보호자가 있을 경우, 해제
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
    private Follow getTargetUser(String userId, String targetId){
        return Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("toUserId").is(targetId)
                .and("fromUserId").is(userId)), Follow.class)).orElseThrow(() -> new ResourceNotFoundException("친구목록에 존재하지 않는 사용자 입니다."));
    }
}
