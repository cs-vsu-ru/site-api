package cs.vsu.is.repository;

import cs.vsu.is.domain.ScientificWorkType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ScientificWorkTypeRepositoryWithBagRelationships {
    Optional<ScientificWorkType> fetchBagRelationships(Optional<ScientificWorkType> scientificWorkType);

    List<ScientificWorkType> fetchBagRelationships(List<ScientificWorkType> scientificWorkTypes);

    Page<ScientificWorkType> fetchBagRelationships(Page<ScientificWorkType> scientificWorkTypes);
}
