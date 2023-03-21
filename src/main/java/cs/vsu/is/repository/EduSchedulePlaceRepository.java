package cs.vsu.is.repository;

import cs.vsu.is.domain.EduSchedulePlace;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EduSchedulePlace entity.
 *
 * When extending this class, extend EduSchedulePlaceRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface EduSchedulePlaceRepository extends EduSchedulePlaceRepositoryWithBagRelationships, JpaRepository<EduSchedulePlace, Long> {
    default Optional<EduSchedulePlace> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<EduSchedulePlace> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<EduSchedulePlace> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
