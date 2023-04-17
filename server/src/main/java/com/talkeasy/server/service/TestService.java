package com.talkeasy.server.service;

import com.talkeasy.server.domain.Test;
import com.talkeasy.server.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TestService {

    private final TestRepository testRepository;
    private final MongoTemplate mongoTemplate;

    public List<Test> getTests() {
        List<Test> test = testRepository.findAll();
        return test;
    }



}
