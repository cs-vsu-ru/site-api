package cs.vsu.is.repository;

import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.ScientificLeaderships;
import cs.vsu.is.domain.ScientificWorkType;
import cs.vsu.is.domain.Students;
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
    List<ScientificLeaderships> findAllByEmployeeAndScientificWorkType(Employee employee, ScientificWorkType scientificWorkType);
    List<ScientificLeaderships> findAllByEmployeeAndScientificWorkTypeAndStudentAndYear(Employee employee, ScientificWorkType scientificWorkType, Students student, Integer year);
}
