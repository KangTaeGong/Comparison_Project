package project.reviews.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.Movie;
import project.reviews.domain.User;
import project.reviews.repository.RecordRepository;
import project.reviews.repository.UserRepository;

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

    public Long save(String movie_title, User user) {

        Movie movie = new Movie(movie_title, userRepository.loadUserByUserId(user.getUserId()));
        recordRepository.save(movie);

        return movie.getId();
    }

}
