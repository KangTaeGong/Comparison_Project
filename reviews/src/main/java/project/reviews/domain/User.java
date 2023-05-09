package project.reviews.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
* 2022-09-15 생성
* 회원 Entity
* */
@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_PK")
    private Long id;

    @Column(length = 40, nullable = false)
    private String userName;

    @Column(length = 40, nullable = false)
    private String userId;

    @Column(length = 50, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Setter
    @Column(length = 20)
    private Role role;

    // 회원 정보가 삭제되면 작성한 게시글과 영화 정보도 같이 삭제
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    List<Movie> movies = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    List<Posting> postings = new ArrayList<>();

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