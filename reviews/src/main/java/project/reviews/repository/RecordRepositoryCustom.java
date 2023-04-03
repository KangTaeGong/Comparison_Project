package project.reviews.repository;

import project.reviews.domain.Movie;
import project.reviews.domain.Posting;

import java.util.List;

/*
* 2023-02-15
* Spring Data JPA에 기능을 추가하기 위한 사용자 정의 Interface
* 영화 검색어와 게시글 정보를 검색
* */
public interface RecordRepositoryCustom {

    List<Movie> searchMovieList(Long userId, int offset, int limit);

    List<Posting> searchPostingList(Long userId, int offset, int limit);
}
