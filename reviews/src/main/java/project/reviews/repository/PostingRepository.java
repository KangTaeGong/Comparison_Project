package project.reviews.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.reviews.domain.Posting;
import project.reviews.dto.PostingResponseDto;

import java.util.List;
import java.util.Optional;

public interface PostingRepository {

    Long create(Posting posting);

    Page<PostingResponseDto> getListPaging(Pageable pageable);

    List<PostingResponseDto> getList();

    Optional<PostingResponseDto> getPosting(Long postingId);

    Long getPostingCount();

    Posting findPostingById(Long postingId);

    void delete_Posting(Long postingId);
}
