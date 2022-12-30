package project.reviews.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.Posting;
import project.reviews.dto.PostingResponseDto;
import project.reviews.exception.PostingNotFoundException;

import java.util.List;

/*
* 2022-09-30
* PostingRepositoryImpl TestCode
* */
@SpringBootTest
@Slf4j
@Transactional
public class PostingRepositoryImplTest {

    @Autowired PostingRepository postingRepository;

    Posting posting1;
    Posting posting2;
    Posting posting3;

    /*
    * 테스트시 사용할 초기값 미리 넣어 두기
    * */
    @BeforeEach
    public void init() {
        posting1 = new Posting("게시글1", "안녕하세요. 홍길동 입니다.", "홍길동", "1234", 1);
        posting2 = new Posting("게시글2", "안녕하세요. 고길동 입니다.", "고길동", "1234", 1);
        posting3 = new Posting("게시글3", "안녕하세요. 김길이 입니다.", "김길이", "1234", 1);

        postingRepository.create(posting1);
        postingRepository.create(posting2);
        postingRepository.create(posting3);
    }


    /*
    * 게시글 저장 테스트
    * create(), getPosting()
    * */
    @Test
//    @Rollback(value = false)
    void postingCreate_Test() {
        //given
        Posting posting = new Posting("첫 게시글1", "안녕하세요. 홍길동 입니다.", "홍길동","1234", 1);

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

        //when
        List<PostingResponseDto> findList = postingRepository.getList();

        //then
        Assertions.assertEquals(3, findList.size());
    }

    /*
    * 전체 게시글 + 페이징 테스트
    * getListPaging()
    * */
    @Test
    void getListPaging_Test() {
        //when
        PageRequest pageRequest = PageRequest.of(0, 3);
        Page<PostingResponseDto> listPaging = postingRepository.getListPaging(pageRequest, "");

        //then
        org.assertj.core.api.Assertions.assertThat(listPaging.getSize()).isEqualTo(3);

        for(PostingResponseDto dto : listPaging) {
            log.info("createdDate = {}",dto.getCreatedDate());
        }
    }
    
    /*
    * 게시글 검색 테스트 + 페이징
    * */
    @Test
    void searchPosting_Test() {
        //when
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<PostingResponseDto> findPosting = postingRepository.getListPaging(pageRequest, "김길");
        //then
        for(PostingResponseDto find : findPosting) {
            log.info("findPosting = {}", find.getContent());
            Assertions.assertEquals(posting3.getTitle(), find.getTitle());
        }
    }

    /*
    * 게시글 삭제 테스트
    * */
    @Test
    void delete_posting_Test() {

        //when
        postingRepository.delete_Posting(posting2.getId());
        List<PostingResponseDto> findList = postingRepository.getList();

        //then
        Assertions.assertEquals(2, findList.size());
    }

    /*
    * 게시글 개수 카운팅 테스트
    * */
    @Test
    void getPostingCount_Test() {

        //when
        Long postingCount = postingRepository.getPostingCount();
        //then (@BeforeEach 3 + TestDataInit 100)
        Assertions.assertEquals(103, postingCount);
    }
}
