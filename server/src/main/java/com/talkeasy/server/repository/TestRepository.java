package com.talkeasy.server.repository;

import com.talkeasy.server.domain.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends MongoRepository<Test, String>{

    List<Test> findByCategory(String s);

}
