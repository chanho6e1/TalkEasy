package com.talkeasy.server.repository;

import com.talkeasy.server.domain.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends MongoRepository<Test, String> {

}
