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
    private LocalDateTime modifiedDate;

    public PostingResponseDto(Long id, String title, String content, String writer, String password, int hits) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.hits = hits;
    }
}
