package cs.vsu.is.repository;

import cs.vsu.is.domain.StaticPages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaticPagesRepository extends JpaRepository<StaticPages, Long> {
}
