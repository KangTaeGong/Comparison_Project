package project.reviews.service;

/*
* 2022-09-17 생성
* UserRepository Test용
* */

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.User;
import project.reviews.login.JoinForm;
import project.reviews.repository.UserRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        JoinForm form = new JoinForm("홍기동", "aaa123", "aaabb111@", "aaabb111@");

        //when
        Long savedId = userService.join(form);
        User findUser = userRepository.findById(savedId);
        //then
        assertEquals(form.getUserId(), findUser.getUserId());
        assertEquals(null, userRepository.findById(12L));
//        assertEquals(user, userRepository.findById(savedId).get());
    }

    /*
    * 2022-09-19
    * 회원 가입 ID 중복 확인 테스트
    * */
    @Test
    void join_validate() {
        //given
        JoinForm form1 = new JoinForm("홍기동", "aaa123", "aaabb111@", "aaabb111@");
        JoinForm form2 = new JoinForm("홍기동", "aaa123", "aaabb222@", "aaabb111@");
        //when
        Long savedId1 = userService.join(form1);
        //then
        assertThatThrownBy(() -> userService.join(form2)).isInstanceOf(IllegalStateException.class);

    }
}
