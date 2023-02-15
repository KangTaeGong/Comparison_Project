package project.reviews.repository;

import project.reviews.domain.Movie;
import project.reviews.domain.Posting;

import java.util.List;

public interface RecordRepositoryCustom {

    List<Movie> searchMovieList(Long userId);

    List<Posting> searchPostingList(Long userId);
}
