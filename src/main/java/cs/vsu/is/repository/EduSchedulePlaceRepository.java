package cs.vsu.is.repository;

import cs.vsu.is.domain.EduSchedulePlace;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EduSchedulePlace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EduSchedulePlaceRepository extends JpaRepository<EduSchedulePlace, Long> {}
