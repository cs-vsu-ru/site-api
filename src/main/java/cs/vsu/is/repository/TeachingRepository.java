package cs.vsu.is.repository;

import cs.vsu.is.domain.Teaching;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Teaching entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeachingRepository extends JpaRepository<Teaching, Long> {}
