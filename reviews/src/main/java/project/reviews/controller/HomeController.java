package project.reviews.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import project.reviews.domain.User;
import project.reviews.login.SessionConst;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false)
                       User loginUser, Model model) {
        /*
        * 세션에 회원 데이터가 없을 시 동작
        * 로그인이 되지 않은 메인 페이지
        * */
        if(loginUser == null) {
            return "main/mainPage";
        }
        
        log.info("로그인 페이지로 이동");
        /*
        * 세션이 유지되면 로그인된 메인페이지로 이동
        * */
        model.addAttribute("user", loginUser);
        return "main/loginMainPage";
    }
}
