package cs.vsu.is.repository;

import cs.vsu.is.domain.Emails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailsRepository extends JpaRepository<Emails, Long> {
}
