package project.reviews.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.Role;
import project.reviews.domain.User;
import project.reviews.dto.FindUserDto;
import project.reviews.exception.JoinFailException;
import project.reviews.login.JoinForm;
import project.reviews.repository.UserRepository;

import java.util.Optional;

/*
* 2022-09-16 생성
* 회원 관련 서비스 로직
* */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    /*
    * 회원 가입
    * 회원 가입시 중복 확인하고, 문제가 없다면 Repository에 넘겨서 DB에 저장.
    * */
    public Long join(JoinForm form) throws JoinFailException{

        validateDuplicateUser(form);    // 중복 회원 검증

        /*
        * 중복 확인이 끝나면, User Entity로 변환해서 DB에 저장
        * 회원으로 가입하기 때문에 ROLE_USER
        * 패스워드 암호화
        * */
        String encodePassword = bCryptPasswordEncoder.encode(form.getPassword());

        User user = new User(form.getUserName(), form.getUserId(), encodePassword, Role.ROLE_USER);
        userRepository.save(user);
        return user.getId();
    }

    // 회원가입시 입력한 아이디와 DB에 입력된 아이디 값을 비교해서 중복 회원가입인지 확인
    private void validateDuplicateUser(JoinForm form) throws JoinFailException {
        Optional<FindUserDto> findUser = userRepository.findByUserId(form.getUserId());
        if(!findUser.isEmpty()) {
            throw new JoinFailException("이미 존재하는 회원입니다.");
        }
    }
    

    // 회원 탈퇴시 패스워드 확인 - 인코딩된 패스워드가 입력한 패스워드와 일치하는지 확인
    public Boolean membership_withdrawal_checkPw(User sessionUser, String input_password) {

        if(bCryptPasswordEncoder.matches(input_password, sessionUser.getPassword())) {
            /*
             * session에서 찾은 sessionUser는 id(PK)값이 없다.
             * 그래서 sessionUser.getUserId()를 통해서 다시 값을 조회해서 PK값이 포함된 완전한 user의 값을 가져온다.
             * */
            User user = userRepository.loadUserByUserId(sessionUser.getUserId());
            userRepository.deleteUser(user);
            return true;
        }
        return false;
    }
}
