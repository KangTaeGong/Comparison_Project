package project.reviews.repository;

import project.reviews.domain.Posting;
import project.reviews.dto.PostingResponseDto;

import java.util.List;
import java.util.Optional;

public interface PostingRepository {

    void create(Posting posting);

    List<PostingResponseDto> getList();

    Optional<PostingResponseDto> getPosting(Long postingId);
}
