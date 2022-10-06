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
@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final UserService userService;
    private final PostingService postingService;

    /*
    * 테스트용 회원 아이디 저장
    * */
    @PostConstruct
    public void init() {
        JoinForm form = new JoinForm("홍길동", "aaa123", "aaaa1234@", "aaaa1234@");
        userService.join(form);
    }
    
    /*
    * 게시글 하나 미리 생성
    * */
    @PostConstruct
    public void init_posting() {
        PostingForm form = new PostingForm("첫 번째 게시글", "안녕하세요", "홍길동", "aaaa1234@");
        postingService.create_posting(form);
    }
}
