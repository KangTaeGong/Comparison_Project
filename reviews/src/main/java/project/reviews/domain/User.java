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
    private Long id;
    private String userName;
    private String userId;
    private String password;

    public User(String userName, String userId, String password) {
        this.userName = userName;
        this.userId = userId;
        this.password = password;
    }
}
