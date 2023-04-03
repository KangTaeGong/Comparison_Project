package project.reviews.repository;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.Movie;
import project.reviews.domain.Role;
import project.reviews.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
* 2023-03-09
* RecordRepository Test Code
* */
@SpringBootTest
@Transactional
@Slf4j
public class RecordRepositoryTest {

    @Autowired RecordRepository recordRepository;
    @Autowired UserRepository userRepository;

    @Test
    void searchMovie_lessThan_Test() {
        //given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime aWeekAgo = now.minusDays(7);

        //when
        List<Movie> limitedMovies = recordRepository.findByCreatedDateLessThan(aWeekAgo);

        //then
        assertEquals(3, limitedMovies.size());
    }

    @Test
    void saveMovieRecord_Test() {
        //given
        User user = new User("테스트", "test1234", "test1234@", Role.ROLE_USER);
        userRepository.save(user);
        Movie movie1 = new Movie("슈퍼보드", user);
        Movie saved_movie = recordRepository.save(movie1);

        //when
        Movie findMovie = recordRepository.findById(saved_movie.getId()).get();
        assertEquals(findMovie.getMovie_title(), "슈퍼보드");

        //then
        List<Movie> all_movies = recordRepository.findAll();
        org.assertj.core.api.Assertions.assertThat(all_movies).extracting("movie_title").contains("슈퍼보드");
    }

    @Test
    void deleteMovieRecord_Test() {
        //given
        User user = new User("테스트", "test1234", "test1234@", Role.ROLE_USER);
        userRepository.save(user);
        Movie movie1 = new Movie("슈퍼보드", user);
        Movie saved_movie = recordRepository.save(movie1);

        //when
        recordRepository.delete(saved_movie);

        //then
        log.info("delete_movie = {}", recordRepository.findById(saved_movie.getId()));
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> recordRepository.findById(saved_movie.getId()).get()).isInstanceOf(NoSuchElementException.class);
    }
}
