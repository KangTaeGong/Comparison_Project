package project.reviews.repository;

import project.reviews.domain.Movie;
import project.reviews.domain.Posting;

import java.util.List;

public interface RecordRepositoryCustom {

    List<Movie> searchMovieList(Long userId, int offset, int limit);

    List<Posting> searchPostingList(Long userId, int offset, int limit);
}
