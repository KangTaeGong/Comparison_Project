package project.reviews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.reviews.domain.Movie;

/*
* 2023-02-13
* SpringData JPA 사용
* */
public interface RecordRepository extends JpaRepository<Movie, Long>, RecordRepositoryCustom {
}
