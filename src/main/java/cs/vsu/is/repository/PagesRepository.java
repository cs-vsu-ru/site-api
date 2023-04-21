package cs.vsu.is.repository;

import cs.vsu.is.domain.Pages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PagesRepository extends JpaRepository<Pages, Long> {}
