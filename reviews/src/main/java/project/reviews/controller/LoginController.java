package project.reviews.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.reviews.domain.User;
import project.reviews.dto.FindUserDto;
import project.reviews.exception.UserNotFoundException;
import project.reviews.login.LoginForm;
import project.reviews.service.LoginService;
import project.reviews.login.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/*
* 2022-09-20
* 로그인 Controller
* */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginPage";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURI, HttpServletRequest request) {

        if(bindingResult.hasErrors()) {
            return "login/loginPage";
        }

        FindUserDto loginUser;

        try {
            loginUser = loginService.login(form.getLoginId(), form.getPassword());
        } catch(UserNotFoundException e) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "login/loginPage";
        }

        /*
        * 로그인 성공 로직
        * 세션이 있으면 세션 반환, 없으면 신규 세션 생성
        * */
        HttpSession session = request.getSession();
        User loginUser_entity = new User(loginUser.getUserName(), loginUser.getUserId(), loginUser.getPassword());

        // 세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_USER, loginUser_entity);

        return "redirect:" + redirectURI;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}
