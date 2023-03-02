package project.reviews.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/*
* 2022-09-20
* 메인 페이지 컨트롤러
* */
@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        /*
        * 세션에 존재하는 회원 정보가 있다면 받아온 후 model을 통해 값을 넘거줌
        * null이면 로그인이 되지 않은 상태로 확인.
        * */
        LoginSessionCheck.check_loginUser(request, model);
        return "main/mainPage";
    }

    @GetMapping("/aboutPage")
    public String aboutPage() {
        return "main/aboutPage";
    }

    @GetMapping("/servicePage")
    public String servicePage() {
        return "main/servicePage";
    }
}