package project.reviews.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.reviews.domain.Message;
import project.reviews.exception.JoinFailException;
import project.reviews.login.JoinForm;
import project.reviews.service.UserService;
import project.reviews.validation.ValidationSequence;

import javax.validation.Valid;

/*
* 2022-09-23
* 회원가입 컨트롤러
* */
@Controller
@RequiredArgsConstructor
public class JoinController {

    private final UserService userService;

    @GetMapping("/join")
    public String joinForm(@ModelAttribute("joinForm") JoinForm form) {
        return "login/joinPage";
    }

    @PostMapping("/join")
    public String join(@Validated(ValidationSequence.class) @ModelAttribute("joinForm") JoinForm form, BindingResult bindingResult,
                       Model model) {

        if(!form.getPassword().equals(form.getCheck_password())) {
            bindingResult.rejectValue("password", "NotEquals");
            bindingResult.rejectValue("check_password", "NotEquals");
            return "login/joinPage";
        }

        if(bindingResult.hasErrors()) {
            return "login/joinPage";
        }

        // 회원 가입
        try {
            userService.join(form);
        } catch(JoinFailException e) {
            model.addAttribute("data", new Message("이미 존재하는 회원입니다.", "/join"));
            return "alert/message";
        }

        model.addAttribute("data", new Message("회원 가입이 완료되었습니다.", "/login"));
        return "alert/message";
    }
}
