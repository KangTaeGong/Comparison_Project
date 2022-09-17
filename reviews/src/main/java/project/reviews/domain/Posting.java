package project.reviews.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
* 2022-09-14 생성
* 게시판 글 관련 정보 Entity
* */
@Entity
@Getter
public class Posting {

    @Id @GeneratedValue
    @Column(name = "posting_id")
    private Long id;

    private String title;   // 글 제목
    private String userName; //글쓴이
    private String hits; // 조회수

}
