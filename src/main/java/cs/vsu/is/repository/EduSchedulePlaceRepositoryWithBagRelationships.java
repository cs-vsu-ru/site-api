package cs.vsu.is.repository;

import cs.vsu.is.domain.EduSchedulePlace;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface EduSchedulePlaceRepositoryWithBagRelationships {
    Optional<EduSchedulePlace> fetchBagRelationships(Optional<EduSchedulePlace> eduSchedulePlace);

    List<EduSchedulePlace> fetchBagRelationships(List<EduSchedulePlace> eduSchedulePlaces);

    Page<EduSchedulePlace> fetchBagRelationships(Page<EduSchedulePlace> eduSchedulePlaces);
}
