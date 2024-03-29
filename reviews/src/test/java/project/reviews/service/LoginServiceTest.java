package project.reviews.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.dto.FindUserDto;
import project.reviews.exception.UserNotFoundException;
import project.reviews.login.JoinForm;
import project.reviews.repository.UserRepository;

@Transactional
@SpringBootTest
public class LoginServiceTest {

    @Autowired LoginService loginService;
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    void loginTest() {
        //given
        JoinForm form = new JoinForm("홍기동", "aaa1234", "aaabb111@", "aaabb111@");
        Long savedId = userService.join(form);

        FindUserDto findUser = null;
        //when
        try {
            findUser = loginService.login("aaa1234", "aaabb111@");
        } catch(UserNotFoundException e) {
            e.printStackTrace();
        }
        //then
        Assertions.assertEquals(form.getUserName(), findUser.getUserName());
    }

    @Test
    void loginException_Test() {
        //given
        JoinForm form = new JoinForm("홍기동", "aaa1234", "aaabb111@", "aaabb111@");
        Long savedId = userService.join(form);

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            loginService.login("aaa34", "aaa1@");
        });
    }
}
