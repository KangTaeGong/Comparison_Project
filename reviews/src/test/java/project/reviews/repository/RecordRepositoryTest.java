package project.reviews.repository;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.Movie;

import java.time.LocalDateTime;
import java.util.List;

/*
* 2023-03-09
* RecordRepository Test Code
* */
@SpringBootTest
@Transactional
@Slf4j
public class RecordRepositoryTest {

    @Autowired RecordRepository recordRepository;

    @Test
    void searchMovie_lessThan_Test() {
        //given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime aWeekAgo = now.minusMinutes(5);

        //when
        List<Movie> limitedMovies = recordRepository.findByCreatedDateLessThan(aWeekAgo);

        //then
        Assertions.assertEquals(5, limitedMovies.size());
    }
}
