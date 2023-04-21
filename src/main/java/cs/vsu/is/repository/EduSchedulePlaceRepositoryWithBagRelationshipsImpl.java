package cs.vsu.is.repository;

import cs.vsu.is.domain.EduSchedulePlace;
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
public class EduSchedulePlaceRepositoryWithBagRelationshipsImpl implements EduSchedulePlaceRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<EduSchedulePlace> fetchBagRelationships(Optional<EduSchedulePlace> eduSchedulePlace) {
        return eduSchedulePlace.map(this::fetchLessons);
    }

    @Override
    public Page<EduSchedulePlace> fetchBagRelationships(Page<EduSchedulePlace> eduSchedulePlaces) {
        return new PageImpl<>(
            fetchBagRelationships(eduSchedulePlaces.getContent()),
            eduSchedulePlaces.getPageable(),
            eduSchedulePlaces.getTotalElements()
        );
    }

    @Override
    public List<EduSchedulePlace> fetchBagRelationships(List<EduSchedulePlace> eduSchedulePlaces) {
        return Optional.of(eduSchedulePlaces).map(this::fetchLessons).orElse(Collections.emptyList());
    }

    EduSchedulePlace fetchLessons(EduSchedulePlace result) {
        return entityManager
            .createQuery(
                "select eduSchedulePlace from EduSchedulePlace eduSchedulePlace left join fetch eduSchedulePlace.lessons where eduSchedulePlace is :eduSchedulePlace",
                EduSchedulePlace.class
            )
            .setParameter("eduSchedulePlace", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<EduSchedulePlace> fetchLessons(List<EduSchedulePlace> eduSchedulePlaces) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, eduSchedulePlaces.size()).forEach(index -> order.put(eduSchedulePlaces.get(index).getId(), index));
        List<EduSchedulePlace> result = entityManager
            .createQuery(
                "select distinct eduSchedulePlace from EduSchedulePlace eduSchedulePlace left join fetch eduSchedulePlace.lessons where eduSchedulePlace in :eduSchedulePlaces",
                EduSchedulePlace.class
            )
            .setParameter("eduSchedulePlaces", eduSchedulePlaces)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
