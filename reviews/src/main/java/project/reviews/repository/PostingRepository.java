package project.reviews.repository;

import project.reviews.domain.Posting;
import project.reviews.dto.PostingResponseDto;

import java.util.List;
import java.util.Optional;

public interface PostingRepository {

    Long create(Posting posting);

    List<PostingResponseDto> getList();

    Optional<PostingResponseDto> getPosting(Long postingId);

    Posting findPostingById(Long postingId);

    void delete_Posting(Long postingId);
}
