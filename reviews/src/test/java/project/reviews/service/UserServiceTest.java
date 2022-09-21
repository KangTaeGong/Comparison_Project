package project.reviews.service;

/*
* 2022-09-17 생성
* UserRepository Test용
* */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.User;
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
        User user = new User("홍기동", "aaa123", "olddogz222@", "olddogz222@");
        //when
        Long savedId = userService.join(user);
        //then
        assertEquals(user, userRepository.findById(savedId));
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
        User user1 = new User("홍기동", "aaa123", "olddogz222@", "olddogz222@");
        User user2 = new User("이길동", "aaa123", "olddogz111@", "olddogz111@");
        //when
        Long savedId1 = userService.join(user1);
        //then
        assertThatThrownBy(() -> userService.join(user2)).isInstanceOf(IllegalStateException.class);

    }
}
