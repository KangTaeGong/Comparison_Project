package project.reviews.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
* 2022-09-14 생성
* 게시판 글 관련 정보 Entity
* */
@Entity
@Getter
@NoArgsConstructor
public class Posting extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posting_pk")
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;   // 제목

    @Column(nullable = false)
    private String content; // 내용

    @Column(length = 40, nullable = false)
    private String writer; //글쓴이

    @Column(nullable = false)
    private int hits; // 조회수

    @Column(length = 50, nullable = false)
    private String password; // 수정, 삭제시 사용할 패스워드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_PK", nullable = false)
    private User user;

    public Posting(String title, String content, String writer, String password, int hits) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.hits = hits;
    }

    public Posting(String title, String content, String writer, String password, int hits, User user) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.hits = hits;
        this.user = user;
    }

    // 조회수 ++
    public void setHits(int hits) {
        this.hits = hits;
    }

    // 게시글 수정시 사용
    public void updateContent(String content) {
        this.content = content;
    }
}