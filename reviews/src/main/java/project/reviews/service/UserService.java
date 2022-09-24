package project.reviews.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.User;
import project.reviews.dto.FindUserDto;
import project.reviews.login.JoinForm;
import project.reviews.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/*
* 2022-09-16 생성
* 회원 관련 서비스 로직
* */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    
    /*
    * 회원 가입
    * 회원 가입시 중복 확인하고, 문제가 없다면 Repository에 넘겨서 DB에 저장.
    * User Entity를 반환하지 않고, User Entity의 id값만 반환해줌
    * */

    public Long join(JoinForm form) {

        validateDuplicateUser(form);    // 중복 회원 검증

        /*
        * 중복 확인이 끝나면, User Entity로 변환해서 DB에 저장
        * */
        User user = new User(form.getUserName(), form.getUserId(), form.getPassword());
        userRepository.save(user);
        return user.getId();
    }

    /*
    * 회원가입시 입력한 아이디와 DB에 입력된 아이디 값을 비교해서 중복 회원가입인지 확인
    * */
    private void validateDuplicateUser(JoinForm form) {
        Optional<FindUserDto> findUser = userRepository.findByUserId(form.getUserId());
        if(!findUser.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
