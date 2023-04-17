package cs.vsu.is.repository;

import cs.vsu.is.domain.Students;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface StudentsRepositoryWithBagRelationships {
    Optional<Students> fetchBagRelationships(Optional<Students> students);

    List<Students> fetchBagRelationships(List<Students> students);

    Page<Students> fetchBagRelationships(Page<Students> students);
}
