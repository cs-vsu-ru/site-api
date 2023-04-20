package cs.vsu.is.repository;

import cs.vsu.is.domain.Slider;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Slider entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SliderRepository extends JpaRepository<Slider, Long> {}
