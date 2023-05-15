package cs.vsu.is.repository;

import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.ScientificLeaderships;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the ScientificLeaderships entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScientificLeadershipsRepository extends JpaRepository<ScientificLeaderships, Long> {
    List<ScientificLeaderships> findAllByEmployee(Employee employee);
    List<ScientificLeaderships> findAllByEmployeeId(Long employee_id);
}
