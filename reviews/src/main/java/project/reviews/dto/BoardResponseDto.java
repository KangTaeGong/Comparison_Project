package project.reviews.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
* 2022-09-27
* 게시물 목록, 게시물 상세 조회에 필요한 Dto
* */
@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private int hits;
    private LocalDateTime modifiedDate;

    public BoardResponseDto(Long id, String title, String content, String writer, int hits, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.hits = hits;
        this.modifiedDate = modifiedDate;
    }
}
