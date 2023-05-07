package cs.vsu.is.repository;

import cs.vsu.is.domain.Schedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Schedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Schedule findByIsActual(Boolean isActual);
    Schedule findFirstByIsActual(Boolean isActual);
}
