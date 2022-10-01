package project.reviews.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
* 2022-09-15 생성
* 회원 Entity
* */
@Entity
@Getter
public class User extends BaseTimeEntity{

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
