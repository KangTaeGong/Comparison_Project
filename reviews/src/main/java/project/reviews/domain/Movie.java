package project.reviews.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
* 2023-02-13
* 영화 검색어를 관리하기 위한 Entity
* */
@Entity
@Getter
@NoArgsConstructor
public class Movie extends CreatedTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_pk")
    private Long id;

    @Column(length = 70, nullable = false)
    private String movie_title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_PK", nullable = false)
    private User user;

    public Movie(String movie_title, User user) {
        this.movie_title = movie_title;
        this.user = user;
    }
}