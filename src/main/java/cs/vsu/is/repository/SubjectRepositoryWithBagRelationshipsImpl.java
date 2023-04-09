package cs.vsu.is.repository;

import cs.vsu.is.domain.Subject;
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
public class SubjectRepositoryWithBagRelationshipsImpl implements SubjectRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Subject> fetchBagRelationships(Optional<Subject> subject) {
        return subject.map(this::fetchLessons).map(this::fetchEmployees);
    }

    @Override
    public Page<Subject> fetchBagRelationships(Page<Subject> subjects) {
        return new PageImpl<>(fetchBagRelationships(subjects.getContent()), subjects.getPageable(), subjects.getTotalElements());
    }

    @Override
    public List<Subject> fetchBagRelationships(List<Subject> subjects) {
        return Optional.of(subjects).map(this::fetchLessons).map(this::fetchEmployees).orElse(Collections.emptyList());
    }

    Subject fetchLessons(Subject result) {
        return entityManager
            .createQuery("select subject from Subject subject left join fetch subject.lessons where subject is :subject", Subject.class)
            .setParameter("subject", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Subject> fetchLessons(List<Subject> subjects) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, subjects.size()).forEach(index -> order.put(subjects.get(index).getId(), index));
        List<Subject> result = entityManager
            .createQuery(
                "select distinct subject from Subject subject left join fetch subject.lessons where subject in :subjects",
                Subject.class
            )
            .setParameter("subjects", subjects)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Subject fetchEmployees(Subject result) {
        return entityManager
            .createQuery("select subject from Subject subject left join fetch subject.employees where subject is :subject", Subject.class)
            .setParameter("subject", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Subject> fetchEmployees(List<Subject> subjects) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, subjects.size()).forEach(index -> order.put(subjects.get(index).getId(), index));
        List<Subject> result = entityManager
            .createQuery(
                "select distinct subject from Subject subject left join fetch subject.employees where subject in :subjects",
                Subject.class
            )
            .setParameter("subjects", subjects)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
