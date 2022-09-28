package project.reviews.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.reviews.domain.Posting;
import project.reviews.dto.PostingRequestDto;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PostingRepository {

    private final EntityManager em;

    public void create(Posting posting) {
        em.persist(posting);
    }

    public void update(PostingRequestDto posting_request) {

    }
}
