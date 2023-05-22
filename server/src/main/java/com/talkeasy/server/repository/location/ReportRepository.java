package com.talkeasy.server.repository.location;

import com.talkeasy.server.domain.location.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<Report, Long> {
    List<Report> findByUserId(String userId);
}
