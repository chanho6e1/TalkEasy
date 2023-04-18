package com.talkeasy.server.service;

import com.mongodb.client.MongoClient;
import com.talkeasy.server.domain.Test;
import com.talkeasy.server.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TestService {
//
    private final TestRepository testRepository;
    private final MongoTemplate mongoTemplate;

    public List<Test> getTests() {
        //Repository 조회 방법
         testRepository.findAll();
        //mongoTemplate 조회 방법
        List<Test> test = mongoTemplate.findAll(Test.class);
        return test;
    }
    public List<Test> getTest() {
        Query query = Query.query(Criteria.where("category").is(1).and("is_noun").is(0));
        List<Test> tests = mongoTemplate.find(query, Test.class);
        return tests;
    }


}
