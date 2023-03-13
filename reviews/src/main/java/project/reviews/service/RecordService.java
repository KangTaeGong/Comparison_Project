package project.reviews.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.Movie;
import project.reviews.domain.Posting;
import project.reviews.domain.User;
import project.reviews.dto.RecordPostingDto;
import project.reviews.repository.RecordRepository;
import project.reviews.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
* 2023-01-12
* 사용자의 검색 기록 관련 비즈니스 로직
* */
@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;

    /*
    * 사용자가 검색한 영화 정보를 DB에 저장
    * User Entity와 연관관계 매핑하여 어느 회원이 영화를 검색했는지 회원 정보를 같이 저장
    * */
    public Long saveMovie(String movie_title, User user) {

        Movie movie = new Movie(movie_title, userRepository.loadUserByUserId(user.getUserId()));
        recordRepository.save(movie);

        return movie.getId();
    }
    
    /*
    * 사용자가 검색한 영화 정보 기록을 가져온다.
    * 영화의 제목만 화면에 뿌려줄 예정이기 때문에 List에 제목만 담아서 반환
    * */
    public List<String> getMovieList(User user) {

        User findUser = userRepository.loadUserByUserId(user.getUserId());
        List<Movie> movies = recordRepository.searchMovieList(findUser.getId(), 0, 10);
        
        List<String> list = new ArrayList<>();
        // 검색 목록에서 중복되는 영화 제목은 하나만 출력
        for (Movie movie : movies) {
            if (!list.contains(movie.getMovie_title()))
                list.add(movie.getMovie_title());

            if (list.size() > 4)
                break;
        }

        return list;
    }

    /*
     * 사용자가 작성한 게시글 정보 기록을 가져온다.
     * 게시글의 제목과, 게시글의 id만 담아서 리턴
     * 게시글의 id는 사용자 정보에서 게시글로 href를 통해 바로 이동할 때 사용
     * */
    public List<RecordPostingDto> getPostingList(User user) {

        User findUser = userRepository.loadUserByUserId(user.getUserId());

        List<Posting> postings = recordRepository.searchPostingList(findUser.getId(), 0, 5);

        return postings.stream().map(p -> new RecordPostingDto(p.getId(), p.getTitle())).collect(Collectors.toList());

    }

    /*
    * 업데이트 날짜를 기준으로 일주일이 넘은 영화 검색 목록은 삭제.
    * */
    public void deleteMovieRecord(Movie movie) {
        recordRepository.delete(movie);
    }
}
