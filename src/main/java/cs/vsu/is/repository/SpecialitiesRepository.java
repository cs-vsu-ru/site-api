package cs.vsu.is.repository;

import cs.vsu.is.domain.Specialities;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Specialities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialitiesRepository extends JpaRepository<Specialities, Long> {}
