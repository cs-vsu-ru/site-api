package cs.vsu.is.repository;

import cs.vsu.is.domain.ScientificLeaderships;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ScientificLeaderships entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScientificLeadershipsRepository extends JpaRepository<ScientificLeaderships, Long> {}
