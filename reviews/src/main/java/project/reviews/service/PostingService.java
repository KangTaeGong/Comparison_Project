package project.reviews.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.Posting;
import project.reviews.dto.PostingForm;
import project.reviews.dto.PostingRequestDto;
import project.reviews.dto.PostingResponseDto;
import project.reviews.exception.PostingNotFoundException;
import project.reviews.repository.PostingRepository;

import java.util.List;

/*
* 2022-10-01
* 게시판 비즈니스 로직
* */
@Service
@Transactional
@RequiredArgsConstructor
public class PostingService {

    private final PostingRepository postingRepository;

    /*
    * 게시글 최초 저장
    * 생성된 id를 Controller로 반환
    * */
    public Long create_posting(PostingForm form) {
        Posting posting = new Posting(form.getTitle(), form.getContent(), form.getWriter(), 1);
        Long posting_id = postingRepository.create(posting);
        return posting_id;
    }

    /*
    * 전체 게시글 조회
    * */
    public List<PostingResponseDto> get_postingList() {
        return postingRepository.getList();
    }
    
    /*
    * 전체 게시글 조회(페이징)
    * 커뮤티니 메인 페이지에 정렬(최대 10개)
    * */
    public Page<PostingResponseDto> getPosting_paging(Pageable pageable) {
        return postingRepository.getListPaging(pageable);
    }
    
    /*
    * 하나의 게시글 조회
    * 게시글에 들어갈 때 사용
    * */
    public PostingResponseDto get_posting(Long postingId) {
        return postingRepository.getPosting(postingId).orElseThrow(PostingNotFoundException::new);
    }

    /*
    * ID로 게시글을 조회해서 가져온 뒤 수정
    * */
    public void update_posting(PostingRequestDto requestDto) {
        Posting findPosting = postingRepository.findPostingById(requestDto.getId());
        findPosting.updateContent(requestDto.getContent());
    }
    
    /*
    * 게시글 삭제(PostingRequestDto)
    * */
    public void delete_posting(PostingRequestDto requestDto) {
        postingRepository.delete_Posting(requestDto.getId());
    }
}