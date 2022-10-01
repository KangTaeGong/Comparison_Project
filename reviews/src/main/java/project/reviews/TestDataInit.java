package project.reviews;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.reviews.login.JoinForm;
import project.reviews.service.UserService;

import javax.annotation.PostConstruct;

/*
* 2022-09-30
* 테스트용 아이디 저장
* */
@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final UserService userService;

    /*
    * 테스트용 회원 아이디 저장
    * */
    @PostConstruct
    public void init() {
        JoinForm form = new JoinForm("홍길동", "aaa123", "aaaa1234@", "aaaa1234@");
        userService.join(form);
    }
}
