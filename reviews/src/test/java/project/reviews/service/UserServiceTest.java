package project.reviews.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.User;
import project.reviews.login.JoinForm;
import project.reviews.repository.UserRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/*
 * 2022-09-17 생성
 * UserRepository Test용
 * */
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    /*
    * 2022-09-17
    * 회원 가입 save 테스트
    * */
    @Test
    void user_join() {
        //given
        JoinForm form = new JoinForm("홍기동", "aaa1234", "aaabb111@", "aaabb111@");

        //when
        Long savedId = userService.join(form);
        User findUser = userRepository.findById(savedId);

        //then
        assertEquals(form.getUserId(), findUser.getUserId());
        assertNull(userRepository.findById(12L));
    }

    /*
    * 2022-09-19
    * 회원 가입 ID 중복 확인 테스트
    * */
    @Test
    void join_validate() {
        //given
        JoinForm form1 = new JoinForm("홍기동", "aaa12344", "aaabb111@", "aaabb111@");
        JoinForm form2 = new JoinForm("홍기동", "aaa12344", "aaabb222@", "aaabb111@");
        //when
        userService.join(form1);
        //then
        assertThatThrownBy(() -> userService.join(form2)).isInstanceOf(IllegalStateException.class);

    }

    @Test
    void membership_withdrawal_pass_Test() {
        //given
        User user = new User("김길이", "kingill4223", "abcd1234@");
        userRepository.save(user);

        //when
        Boolean delete_result = userService.membership_withdrawal_checkPw(user, "abcd1234@");

        //then
        Assertions.assertEquals(true, delete_result);
    }
}
