package project.reviews.login;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

/*
* 2022-09-20
* 로그인시 입력받는 값
* */
@Getter
public class LoginForm {

    @NotEmpty(message = "아이디를 입력해주세요.")
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    public LoginForm(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
