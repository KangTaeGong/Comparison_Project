package project.reviews.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.Movie;
import project.reviews.domain.User;
import project.reviews.login.JoinForm;
import project.reviews.repository.RecordRepository;
import project.reviews.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/*
 * 2023-01-13
 * RecordService TestCode
 * */
@SpringBootTest
@Transactional
@Slf4j
public class RecordServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired RecordService recordService;
    @Autowired
    RecordRepository recordRepository;

    @Test
    void save() {
        //given
        User user1 = new User("홍길동", "aaa1223233", "aaaa1234@");
        User user2 = new User("홍김동", "aaa1234", "1221@");

        userRepository.save(user1);
        userRepository.save(user2);

        //when
        recordService.saveMovie("공조2", user1);
        recordService.saveMovie("공조1", user2);

        List<Movie> findMovie = recordRepository.findAll();
        //then
        Assertions.assertEquals(2, findMovie.size());
    }

    @Test
    void findById() {
        //given
        User user1 = new User("홍길동", "aaa1223233", "aaaa1234@");
        User user2 = new User("홍김동", "aaa1234", "1221@");

        userRepository.save(user1);
        userRepository.save(user2);

        //when
        Long saved_movie1 = recordService.saveMovie("공조2", user1);
        recordService.saveMovie("공조1", user2);

        //then
        Movie search_Movie = recordRepository.findById(saved_movie1).get();
        User search_User = search_Movie.getUser();

        Assertions.assertEquals("홍길동", search_User.getUserName());
    }
}
