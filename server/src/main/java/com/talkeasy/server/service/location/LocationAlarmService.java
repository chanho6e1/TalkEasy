package com.talkeasy.server.service.location;

import com.talkeasy.server.common.exception.ResourceNotFoundException;
import com.talkeasy.server.domain.location.SOS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LocationAlarmService {
    private final MongoTemplate mongoTemplate;

    //채팅방 생성
    public String createRoom(String name) {
        mongoTemplate.insert(SOS.builder().title(name).build());
        return "/sub/" + name;
    }
    // 채팅방 삭제
    public String deleteRoom(String name){
        SOS sos = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("title").is(name)), SOS.class)).orElseThrow(() -> new ResourceNotFoundException("sos", "title", name));;
        mongoTemplate.remove(sos);
        return name;
    }

    // 채팅방 존재 유무 확인
    public boolean isRoom(String name){
        SOS sos = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("title").is(name)), SOS.class)).orElse(null);
        return sos == null ? false : true;
    }
}
