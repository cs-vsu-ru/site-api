package cs.vsu.is.repository;

import cs.vsu.is.domain.ScientificWorkType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ScientificWorkType entity.
 *
 * When extending this class, extend ScientificWorkTypeRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ScientificWorkTypeRepository
    extends ScientificWorkTypeRepositoryWithBagRelationships, JpaRepository<ScientificWorkType, Long> {
    default Optional<ScientificWorkType> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<ScientificWorkType> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<ScientificWorkType> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
