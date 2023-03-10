package project.reviews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.reviews.domain.Movie;

import java.time.LocalDateTime;
import java.util.List;

/*
* 2023-02-13
* SpringData JPA 사용
* */
public interface RecordRepository extends JpaRepository<Movie, Long>, RecordRepositoryCustom {
    List<Movie> findByCreatedDateLessThan(LocalDateTime aWeekAgo);
}
