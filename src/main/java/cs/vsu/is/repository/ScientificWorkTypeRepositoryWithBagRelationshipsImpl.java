package cs.vsu.is.repository;

import cs.vsu.is.domain.ScientificWorkType;
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
public class ScientificWorkTypeRepositoryWithBagRelationshipsImpl implements ScientificWorkTypeRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ScientificWorkType> fetchBagRelationships(Optional<ScientificWorkType> scientificWorkType) {
        return scientificWorkType.map(this::fetchScientificLeadeships);
    }

    @Override
    public Page<ScientificWorkType> fetchBagRelationships(Page<ScientificWorkType> scientificWorkTypes) {
        return new PageImpl<>(
            fetchBagRelationships(scientificWorkTypes.getContent()),
            scientificWorkTypes.getPageable(),
            scientificWorkTypes.getTotalElements()
        );
    }

    @Override
    public List<ScientificWorkType> fetchBagRelationships(List<ScientificWorkType> scientificWorkTypes) {
        return Optional.of(scientificWorkTypes).map(this::fetchScientificLeadeships).orElse(Collections.emptyList());
    }

    ScientificWorkType fetchScientificLeadeships(ScientificWorkType result) {
        return entityManager
            .createQuery(
                "select scientificWorkType from ScientificWorkType scientificWorkType left join fetch scientificWorkType.scientificLeadeships where scientificWorkType is :scientificWorkType",
                ScientificWorkType.class
            )
            .setParameter("scientificWorkType", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<ScientificWorkType> fetchScientificLeadeships(List<ScientificWorkType> scientificWorkTypes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, scientificWorkTypes.size()).forEach(index -> order.put(scientificWorkTypes.get(index).getId(), index));
        List<ScientificWorkType> result = entityManager
            .createQuery(
                "select distinct scientificWorkType from ScientificWorkType scientificWorkType left join fetch scientificWorkType.scientificLeadeships where scientificWorkType in :scientificWorkTypes",
                ScientificWorkType.class
            )
            .setParameter("scientificWorkTypes", scientificWorkTypes)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
