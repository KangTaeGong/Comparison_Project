package project.reviews;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.reviews.dto.PostingForm;
import project.reviews.login.JoinForm;
import project.reviews.service.PostingService;
import project.reviews.service.UserService;

import javax.annotation.PostConstruct;

/*
* 2022-09-30
* 테스트용 아이디 저장
* */
//@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final UserService userService;
    private final PostingService postingService;

    /*
    * 테스트용 회원 아이디 저장
    * 게시글 하나 미리 생성
    * */
    @PostConstruct
    public void init() {
        JoinForm form = new JoinForm("홍길동", "aaa123", "aaaa1234@", "aaaa1234@");
        userService.join(form);

        for(int i = 1; i < 51; i++) {
            PostingForm postingForm = new PostingForm(i+"번 게시글", "안녕하세요"+i, "홍길동"+i, "aaaa1234@");
            postingService.create_posting(postingForm, "aaa123");
        }
    }
}
