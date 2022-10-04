package project.reviews.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

/*
* 2022-10-01
* 최초 게시판 글 작성시 필요한 정보 DTO
* */
@Getter
public class PostingForm {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String writer;

    public PostingForm(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
