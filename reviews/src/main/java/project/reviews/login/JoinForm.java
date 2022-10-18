package project.reviews.login;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/*
 * 2022-09-22
 * 회원가입 시 회원의 정보를 입력받는 form
 * */
@Getter
public class JoinForm {

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$")
    private String userName;

    @NotBlank
    @Pattern(regexp = "[a-z0-9]{5,9}")
    private String userId;

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}")
    private String password;

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}")
    private String check_password;  // 2차 비밀번호

    public JoinForm(String userName, String userId, String password, String check_password) {
        this.userName = userName;
        this.userId = userId;
        this.password = password;
        this.check_password = check_password;
    }
}
