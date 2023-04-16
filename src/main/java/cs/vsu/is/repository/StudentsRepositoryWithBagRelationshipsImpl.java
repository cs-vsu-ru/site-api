package cs.vsu.is.repository;

import cs.vsu.is.domain.Students;
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
public class StudentsRepositoryWithBagRelationshipsImpl implements StudentsRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Students> fetchBagRelationships(Optional<Students> students) {
        return students.map(this::fetchScientificLeadeships);
    }

    @Override
    public Page<Students> fetchBagRelationships(Page<Students> students) {
        return new PageImpl<>(fetchBagRelationships(students.getContent()), students.getPageable(), students.getTotalElements());
    }

    @Override
    public List<Students> fetchBagRelationships(List<Students> students) {
        return Optional.of(students).map(this::fetchScientificLeadeships).orElse(Collections.emptyList());
    }

    Students fetchScientificLeadeships(Students result) {
        return entityManager
            .createQuery(
                "select students from Students students left join fetch students.scientificLeadeships where students is :students",
                Students.class
            )
            .setParameter("students", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Students> fetchScientificLeadeships(List<Students> students) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, students.size()).forEach(index -> order.put(students.get(index).getId(), index));
        List<Students> result = entityManager
            .createQuery(
                "select distinct students from Students students left join fetch students.scientificLeadeships where students in :students",
                Students.class
            )
            .setParameter("students", students)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
