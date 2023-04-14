package cs.vsu.is.repository;

import cs.vsu.is.domain.ScientificWorkType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ScientificWorkType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScientificWorkTypeRepository extends JpaRepository<ScientificWorkType, Long> {}
