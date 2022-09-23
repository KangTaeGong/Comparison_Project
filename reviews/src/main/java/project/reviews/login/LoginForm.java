package project.reviews.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

/*
* 2022-09-20
* 로그인시 입력받는 값
* */

@Data
@AllArgsConstructor
public class LoginForm {

    @NotEmpty(message = "아이디를 입력해주세요.")
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;
}
