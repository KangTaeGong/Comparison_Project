package project.reviews.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.Movie;
import project.reviews.domain.Posting;
import project.reviews.domain.User;
import project.reviews.service.RecordService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/*
* 2023-02-15
* RecordRepositoryCustomImpl Test Code
* */
@SpringBootTest
@Transactional
@Slf4j
public class RecordRepositoryCustomImplTest {

    @Autowired UserRepositoryImpl userRepository;
    @Autowired RecordService recordService;
    @Autowired RecordRepository recordRepositoryCustom;
    @Autowired PostingRepository postingRepository;
    /*
    * 회원 아이디를 통해 회원이 검색한 영화 제목 리스트를 가져오는 메서드 테스트
    * */
    @Test
    void searchMovieList_Test() {
        //given
        User user1 = new User("김길이", "abc123", "abcd1234@");
        User user2 = new User("김길삼", "zbc321", "abcd1234@");

        userRepository.save(user1);
        userRepository.save(user2);

        recordService.saveMovie("공조1", user1);
        recordService.saveMovie("공조2", user1);

        recordService.saveMovie("터미네이터1", user2);
        recordService.saveMovie("터미네이터2", user2);
        recordService.saveMovie("터미네이터3", user2);

        //when
        List<Movie> movies = recordRepositoryCustom.searchMovieList(user2.getId(), 0, 3);

        //then
        assertThat(movies)
                .extracting("movie_title")
                .containsExactly("터미네이터3", "터미네이터2", "터미네이터1");

        Assertions.assertEquals(3, movies.size());
    }

    /*
    * 회원 아이디를 통해 회원이 작성한 게시글 리스트를 가져오는 메서드 테스트
    * */
    @Test
    void searchPostingList_Test() {
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
        List<Posting> postings = recordRepositoryCustom.searchPostingList(user2.getId(), 0, 3);

        //then
        assertThat(postings)
                .extracting("title")
                .containsExactly("테스트 게시글5","테스트 게시글4", "테스트 게시글3");

        Assertions.assertEquals(3, postings.size());
    }
}
