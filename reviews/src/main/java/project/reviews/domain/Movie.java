package project.reviews.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Movie extends CreatedTimeEntity {

    @Id @GeneratedValue
    private Long id;
    private String movie_title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_PK")
    private User user;

    public Movie(String movie_title, User user) {
        this.movie_title = movie_title;
        this.user = user;
    }
}
