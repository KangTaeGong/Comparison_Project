package project.reviews.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.Posting;
import project.reviews.dto.PostingResponseDto;
import project.reviews.exception.PostingNotFoundException;

import java.util.List;

/*
* 20022-09-30
* PostingRepositoryImpl TestCode
* */
@SpringBootTest
@Slf4j
@Transactional
public class PostingRepositoryImplTest {

    @Autowired PostingRepository postingRepository;

    /*
    * 게시글 저장 테스트
    * create(), getPosting()
    * */
    @Test
//    @Rollback(value = false)
    void postingCreate_Test() {
        //given
        Posting posting = new Posting("첫 게시글1", "안녕하세요. 홍길동 입니다.", "홍길동", 1);

        //when - 게시글 저장
        postingRepository.create(posting);

        // id값을 통해서 DB에서 저장한 포스팅 검색
        PostingResponseDto findPosting = postingRepository.getPosting(posting.getId()).orElseThrow(PostingNotFoundException::new);

        //then
        Assertions.assertEquals(posting.getId(), findPosting.getId());
    }

    /*
    * 전체 게시글 가져오는 테스트
    * getList()
    * */
    @Test
    void posting_getList_Test() {
        //given
        Posting posting1 = new Posting("게시글1", "안녕하세요. 홍길동 입니다.", "홍길동", 1);
        Posting posting2 = new Posting("게시글2", "안녕하세요. 고길동 입니다.", "고길동", 1);
        Posting posting3 = new Posting("게시글3", "안녕하세요. 김길이 입니다.", "김길이", 1);

        //when
        postingRepository.create(posting1);
        postingRepository.create(posting2);
        postingRepository.create(posting3);

        List<PostingResponseDto> findList = postingRepository.getList();
        //then
        Assertions.assertEquals(3, findList.size());
    }
    
    /*
    * 게시글 삭제 테스트
    * */
    @Test
    void delete_posting_Test() {
        //given
        Posting posting1 = new Posting("게시글1", "안녕하세요. 홍길동 입니다.", "홍길동", 1);
        Posting posting2 = new Posting("게시글2", "안녕하세요. 고길동 입니다.", "고길동", 1);
        Posting posting3 = new Posting("게시글3", "안녕하세요. 김길이 입니다.", "김길이", 1);

        //when
        postingRepository.create(posting1);
        postingRepository.create(posting2);
        postingRepository.create(posting3);

        postingRepository.delete_Posting(posting2.getId());
        List<PostingResponseDto> findList = postingRepository.getList();

        //then
        Assertions.assertEquals(2, findList.size());
    }
}
