package project.reviews.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.dto.FindUserDto;
import project.reviews.exception.UserNotFoundException;
import project.reviews.repository.UserRepository;

/*
* 2022-09-20
* Login 관련 Service 로직
* */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LoginService {
    
    private final UserRepository userRepository;

    public FindUserDto login(String userId, String password) throws UserNotFoundException {

        FindUserDto findUser = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(password, findUser.getPassword())) {
            return findUser;
        } else {
            throw new UserNotFoundException("비밀번호가 일치하지 않습니다.");
        }
    }
    
}
