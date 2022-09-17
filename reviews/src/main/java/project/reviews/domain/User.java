package project.reviews.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/*
* 2022-09-15 생성
* 회원 Entity
* 회원가입 할 때 사용하기 위해 각각 조건을 달아둠
* */
@Entity
@Getter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotBlank(message = "회원 이름은 필수 입력 값입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$",
            message = "이름 형식이 올바르지 않습니다.")
    private String userName;

    @NotBlank(message = "회원 아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "[a-z0-9]{2,9}",
            message = "아이디는 5 ~ 20자의 영문 소문자, 숫자만 가능합니다.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해 주세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 8 ~ 16자의 영문, 숫자, 특수문자 조합으로 가능합니다.")
    private String password;

    @NotBlank(message = "2차 비밀번호를 입력해 주세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 8 ~ 16자의 영문, 숫자, 특수문자 조합으로 가능합니다.")
    private String check_password;  // 2차 비밀번호

}
