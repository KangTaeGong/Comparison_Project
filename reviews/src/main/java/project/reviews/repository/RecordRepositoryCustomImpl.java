package project.reviews.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.reviews.domain.Movie;
import project.reviews.domain.Posting;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class RecordRepositoryCustomImpl implements RecordRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<Movie> searchMovieList(Long userId) {
        return em.createQuery("select m from Movie m" +
                               " join m.user u" +
                               " where u.id =: userId", Movie.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Posting> searchPostingList(Long userId) {
        return em.createQuery("select p from Posting p" +
                                " join p.user u" +
                                " where u.id =: userId", Posting.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
