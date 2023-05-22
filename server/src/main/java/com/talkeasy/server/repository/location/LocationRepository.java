package com.talkeasy.server.repository.location;

import com.talkeasy.server.domain.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findTopByUserIdOrderByDateTimeDesc(String userId);

    List<Location> findByUserIdAndDateTimeAfter(String userId, LocalDateTime startTimeOfToday);
}
