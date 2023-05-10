package com.talkeasy.server.repository.location;

import com.talkeasy.server.domain.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface LocationRepository extends JpaRepository<Location, Long> {


}
