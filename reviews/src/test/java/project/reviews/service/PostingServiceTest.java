package project.reviews.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.Posting;
import project.reviews.dto.PostingForm;
import project.reviews.dto.PostingRequestDto;
import project.reviews.dto.PostingResponseDto;
import project.reviews.exception.PostingNotFoundException;
import project.reviews.repository.PostingRepository;

import java.util.List;

@SpringBootTest
@Transactional
public class PostingServiceTest {

    @Autowired PostingService postingService;
    @Autowired PostingRepository postingRepository;

    Posting posting1;
    Posting posting2;
    Posting posting3;

    /*
     * 테스트시 사용할 초기값 미리 넣어 두기
     * */
    @BeforeEach
    public void init() {
        posting1 = new Posting("게시글1", "안녕하세요. 홍길동 입니다.", "홍길동", 1);
        posting2 = new Posting("게시글2", "안녕하세요. 고길동 입니다.", "고길동", 1);
        posting3 = new Posting("게시글3", "안녕하세요. 김길이 입니다.", "김길이", 1);

        postingRepository.create(posting1);
        postingRepository.create(posting2);
        postingRepository.create(posting3);
    }

    /*
    * 게시글 저장 테스트
    * */
    @Test
    void create_posting_Test() {
        //given
        PostingForm form = new PostingForm("최초 생성 게시글", "안녕하세요.", "시금치");
        //when
        Long postingId = postingService.create_posting(form);
        //then
        PostingResponseDto findPosting = postingRepository.getPosting(postingId).orElseThrow(PostingNotFoundException::new);
        Assertions.assertEquals("시금치", findPosting.getWriter());
    }

    /*
    * 전체 게시글 조회 테스트
    * */
    @Test
    void get_postingList_Test() {
        //when
        List<PostingResponseDto> postingList = postingService.get_postingList();
        //then
        Assertions.assertEquals(3, postingList.size());
    }

    @Test
    void get_posting_Test() {
        //when
        Long postingId = posting2.getId();
        PostingResponseDto posting = postingService.get_posting(postingId);
        //then
        Assertions.assertEquals("게시글2", posting.getTitle());
    }

    /*
    * 하나의 게시글 조회 테스트
    * */

    /*
     * 게시글 수정 테스트
     * */
    @Test
    void update_posting_Test() {
        //given
        PostingRequestDto requestDto = new PostingRequestDto(posting3.getId(), posting3.getTitle(), "수정된 내용입니다.", posting3.getWriter());

        //when
        postingService.update_posting(requestDto);

        //then
        PostingResponseDto findPosting = postingRepository.getPosting(posting3.getId()).orElseThrow(PostingNotFoundException::new);
        Assertions.assertEquals("수정된 내용입니다.", findPosting.getContent());
    }
}