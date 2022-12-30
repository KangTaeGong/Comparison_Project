package project.reviews.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
* 2022-09-27
* 게시물 목록, 게시물 상세 조회에 필요한 Dto
* */
@Getter
@NoArgsConstructor
public class PostingResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String password;
    private int hits;
    private String createdDate;

    // paging 포함 조회시 사용(전체 게시글 송출)
    public PostingResponseDto(Long id, String title, String content, String writer, String password, int hits, String createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.hits = hits;
        this.createdDate = createdDate;
    }

    // paging없이 단순 조회(게시글 조회 등)
    public PostingResponseDto(Long id, String title, String content, String writer, String password, int hits) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.hits = hits;
    }
}
