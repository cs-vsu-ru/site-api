package cs.vsu.is.repository;

import cs.vsu.is.domain.ScientificLeadeship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ScientificLeadeship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScientificLeadeshipRepository extends JpaRepository<ScientificLeadeship, Long> {}
