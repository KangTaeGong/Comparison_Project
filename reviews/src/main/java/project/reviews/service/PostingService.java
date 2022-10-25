package project.reviews.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.Posting;
import project.reviews.dto.PostingForm;
import project.reviews.dto.PostingModifyForm;
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
        Posting posting = new Posting(form.getTitle(), form.getContent(), form.getWriter(), form.getPassword(), 1);
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
    * 커뮤니티 메인 페이지에 정렬(최대 15개)
    * */
    public Page<PostingResponseDto> getPosting_paging(Pageable pageable, String postingInfo) {
        return postingRepository.getListPaging(pageable, postingInfo);
    }

    /*
    * 게시글 개수 조회
    * 게시글 번호 지정시 사용
    * */
    public Long getPostingCount() {
        return postingRepository.getPostingCount();
    }

    /*
    * 하나의 게시글 조회
    * 게시글에 들어갈 때 사용
    * */
    public PostingResponseDto get_posting(Long postingId) {
        return postingRepository.getPosting(postingId).orElseThrow(PostingNotFoundException::new);
    }

    /*
    * 사용자가 게시글 조회시 사용
    * 조회수 +1
    * */
    public void update_hits(Long postingId) {
        Posting findPosting = postingRepository.findPostingById(postingId);
        int hits = findPosting.getHits() + 1;
        findPosting.setHits(hits);
    }

    /*
    * ID로 게시글 조회 후 입력한 패스워드와 비교
    * 수정/삭제시 사용
    * 패스워드가 일치하지 않으면 null 반환
    * */
    public PostingResponseDto getPosting_password(Long postingId, String password) {
        PostingResponseDto findPosting = get_posting(postingId);
        if (!findPosting.getPassword().equals(password)) {
            return null;
        }
        return findPosting;
    }

    /*
    * ID로 게시글을 조회해서 가져온 뒤 수정
    * Dirty Checking
    * */
    public void update_posting(PostingModifyForm modifyForm) {
        Posting findPosting = postingRepository.findPostingById(modifyForm.getId());
        findPosting.updateContent(modifyForm.getContent());
    }

    /*
    * 게시글 삭제(PostingRequestDto)
    * */
    public void delete_posting(Long postingId) {
        postingRepository.delete_Posting(postingId);
    }
}