package project.reviews.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.dto.FindUserDto;
import project.reviews.repository.UserRepository;

/*
* 2022-09-20
* Login 관련 Service 로직
* */
@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {
    
    private final UserRepository userRepository;

    /*
    * return값이 null이면 로그인 실패
    * */
    public FindUserDto login(String userId, String password) {

        return userRepository.findByUserId(userId)
                .filter(user -> user.getPassword().equals(password))
                .orElse(null);
    }
    
}
