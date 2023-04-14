package cs.vsu.is.repository;

import cs.vsu.is.domain.Schedule;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ScheduleRepositoryWithBagRelationshipsImpl implements ScheduleRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Schedule> fetchBagRelationships(Optional<Schedule> schedule) {
        return schedule.map(this::fetchLessons);
    }

    @Override
    public Page<Schedule> fetchBagRelationships(Page<Schedule> schedules) {
        return new PageImpl<>(fetchBagRelationships(schedules.getContent()), schedules.getPageable(), schedules.getTotalElements());
    }

    @Override
    public List<Schedule> fetchBagRelationships(List<Schedule> schedules) {
        return Optional.of(schedules).map(this::fetchLessons).orElse(Collections.emptyList());
    }

    Schedule fetchLessons(Schedule result) {
        return entityManager
            .createQuery(
                "select schedule from Schedule schedule left join fetch schedule.lessons where schedule is :schedule",
                Schedule.class
            )
            .setParameter("schedule", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Schedule> fetchLessons(List<Schedule> schedules) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, schedules.size()).forEach(index -> order.put(schedules.get(index).getId(), index));
        List<Schedule> result = entityManager
            .createQuery(
                "select distinct schedule from Schedule schedule left join fetch schedule.lessons where schedule in :schedules",
                Schedule.class
            )
            .setParameter("schedules", schedules)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
