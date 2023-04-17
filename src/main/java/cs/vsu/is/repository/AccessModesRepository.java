package cs.vsu.is.repository;

import cs.vsu.is.domain.AccessModes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AccessModes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccessModesRepository extends JpaRepository<AccessModes, Long> {}
