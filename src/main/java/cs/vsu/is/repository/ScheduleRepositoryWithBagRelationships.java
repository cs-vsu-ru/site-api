package cs.vsu.is.repository;

import cs.vsu.is.domain.Schedule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ScheduleRepositoryWithBagRelationships {
    Optional<Schedule> fetchBagRelationships(Optional<Schedule> schedule);

    List<Schedule> fetchBagRelationships(List<Schedule> schedules);

    Page<Schedule> fetchBagRelationships(Page<Schedule> schedules);
}
