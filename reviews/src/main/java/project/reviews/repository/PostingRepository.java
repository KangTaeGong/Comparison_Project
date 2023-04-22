package project.reviews.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.reviews.domain.Posting;
import project.reviews.dto.PostingResponseDto;

import java.util.List;
import java.util.Optional;

/*
* 2022-09-30
* */
public interface PostingRepository {

    Long create(Posting posting);

    Page<PostingResponseDto> getListPaging(Pageable pageable, String postingInfo);

    List<PostingResponseDto> getList();

    Optional<PostingResponseDto> getPosting(Long postingId);

    Long getPostingCount();

    Posting findPostingById(Long postingId);

    void delete_Posting(Long postingId);
}