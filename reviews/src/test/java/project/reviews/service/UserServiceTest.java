package project.reviews.service;

/*
* 2022-09-17 생성
* UserRepository Test용
* */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.User;
import project.reviews.repository.UserRepository;

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
        Assertions.assertEquals(user, userRepository.findById(savedId).get());
    }
}
