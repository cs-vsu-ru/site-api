package cs.vsu.is.repository;

import cs.vsu.is.domain.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Size;

/**
 * Spring Data JPA repository for the Employee entity.
 *
 * When extending this class, extend EmployeeRepositoryWithBagRelationships too.
 * For more information refer to
 * https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByUserLastName(@Size(max = 50) String user_lastName);

}
