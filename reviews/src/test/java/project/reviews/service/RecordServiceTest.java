package project.reviews.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.Movie;
import project.reviews.domain.Posting;
import project.reviews.domain.User;
import project.reviews.dto.RecordPostingDto;
import project.reviews.repository.PostingRepository;
import project.reviews.repository.RecordRepository;
import project.reviews.repository.UserRepository;

import java.nio.charset.StandardCharsets;
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

    @Autowired UserRepository userRepository;
    @Autowired PostingRepository postingRepository;
    @Autowired RecordService recordService;
    @Autowired RecordRepository recordRepository;

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

    @Test
    void getMovieList_Test() {
        //given
        User user1 = new User("홍김동", "aaa1234", "1221@");
        userRepository.save(user1);


        recordService.saveMovie("터미네이터1", user1);
        recordService.saveMovie("터미네이터2", user1);
        recordService.saveMovie("터미네이터3", user1);

        //when
        List<String> movieList = recordService.getMovieList(user1);

        //then
        for(String movie : movieList) {
            log.info("movie_title = {}", movie);
        }

        Assertions.assertEquals(3, movieList.size());
    }

    @Test
    void getPostingList_Test() {
        //given
        User user1 = new User("김길이", "abc123", "abcd1234@");
        User user2 = new User("김길삼", "zbc321", "abcd1234@");

        userRepository.save(user1);
        userRepository.save(user2);

        Posting posting1 = new Posting("테스트 게시글1", "내용입니다.", "김길이", "abcd1233", 1, user1);
        Posting posting2 = new Posting("테스트 게시글2", "내용입니다.", "김길이", "abcd1233", 1, user1);

        Posting posting3 = new Posting("테스트 게시글3", "내용입니다.", "김길삼", "abcd1233", 1, user2);
        Posting posting4 = new Posting("테스트 게시글4", "내용입니다.", "김길삼", "abcd1233", 1, user2);
        Posting posting5 = new Posting("테스트 게시글5", "내용입니다.", "김길삼", "abcd1233", 1, user2);

        postingRepository.create(posting1);
        postingRepository.create(posting2);
        postingRepository.create(posting3);
        postingRepository.create(posting4);
        postingRepository.create(posting5);

        //when
        List<RecordPostingDto> postingList = recordService.getPostingList(user2);

        //then
        for(RecordPostingDto posting : postingList) {
            log.info("#posting id = {}", posting.getPostingId());
            log.info("#posting title = {}", posting.getPostingTitle());
        }

        Assertions.assertEquals(3, postingList.size());
    }
}
