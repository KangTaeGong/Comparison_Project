package project.reviews.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* 2022-09-27
* 게시물 수정, 조회에 필요한 Dto
* */
@Getter
@NoArgsConstructor
public class PostingRequestDto {

    private Long id;
    private String title;
    private String content;
    private String writer;

    public PostingRequestDto(Long id, String title, String content, String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
