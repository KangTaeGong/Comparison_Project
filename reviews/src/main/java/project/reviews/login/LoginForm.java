package project.reviews.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/*
* 2022-09-20
* 로그인시 입력받는 값
* */
@Data
public class LoginForm {

    @NotEmpty
    private String LoginId;

    @NotEmpty
    private String password;
}
