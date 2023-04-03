package project.reviews.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.reviews.domain.Movie;
import project.reviews.domain.Posting;

import javax.persistence.EntityManager;
import java.util.List;

/*
* 2023-02-16
* 사용자 정의 Repository
* Spring Data JPA에서 사용(RecordRepository)
* */
@RequiredArgsConstructor
public class RecordRepositoryCustomImpl implements RecordRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<Movie> searchMovieList(Long userId, int offset, int limit) {
        return em.createQuery("select m from Movie m" +
                               " where m.user.id =: userId" +
                               " order by m.createdDate desc", Movie.class)
                .setParameter("userId", userId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Posting> searchPostingList(Long userId, int offset, int limit) {
        return em.createQuery("select p from Posting p" +
                                " where p.user.id =: userId" +
                                " order by p.createdDate desc", Posting.class)
                .setParameter("userId", userId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
