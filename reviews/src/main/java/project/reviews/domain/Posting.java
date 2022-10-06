package project.reviews.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/*
* 2022-09-14 생성
* 게시판 글 관련 정보 Entity
* */
@Entity
@Getter
@NoArgsConstructor
public class Posting extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "posting_id")
    private Long id;

    private String title;   // 제목
    private String content; // 내용
    private String writer; //글쓴이
    private int hits; // 조회수
    
    private String password; // 수정, 삭제시 사용할 패스워드

    public Posting(String title, String content, String writer, String password, int hits) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.hits = hits;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
