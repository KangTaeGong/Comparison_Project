package project.reviews.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.reviews.service.UserService;

import javax.validation.Valid;

/*
* 2022-09-23
* 회원가입 컨트롤러
* */
@Controller
@RequiredArgsConstructor
public class JoinController {

    private final UserService userService;

    /*
     * WebDataBinder - 스프링의 파라미터 바인딩의 역할을 해주고 검증 기능도 내부에 포함
     */
    @InitBinder
    public void init(WebDataBinder dataBinder) {
        JoinValidator joinValidator = new JoinValidator();
        dataBinder.addValidators(joinValidator);
    }

    /*
     * 2022-09-23
     * 회원가입 관련 로직
     * */
    @GetMapping("/join")
    public String joinForm(@ModelAttribute("joinForm") JoinForm form) {
        return "login/joinPage";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute("joinForm") JoinForm form, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "login/joinPage";
        }

        /*
         * 회원 가입
         * */
        userService.join(form);

        return "redirect:/loginForm";
    }

}
