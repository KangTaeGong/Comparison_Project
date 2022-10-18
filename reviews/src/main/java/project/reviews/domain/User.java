package project.reviews.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/*
* 2022-09-15 생성
* 회원 Entity
* */
@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity{

    @Id @GeneratedValue
    private Long id;
    private String userName;
    private String userId;
    private String password;

    @Enumerated(EnumType.STRING)
    @Setter
    private Role role;

    public User(String userName, String userId, String password) {
        this.userName = userName;
        this.userId = userId;
        this.password = password;
    }

    public User(String userName, String userId, String password, Role role) {
        this.userName = userName;
        this.userId = userId;
        this.password = password;
        this.role = role;
    }
}
