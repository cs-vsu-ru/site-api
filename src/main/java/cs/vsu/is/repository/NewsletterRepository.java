package cs.vsu.is.repository;

import cs.vsu.is.domain.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {
    List<Newsletter> findAllByNewsletterDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
