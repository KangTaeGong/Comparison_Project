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
    
    /*
    * 수정, 삭제를 위한 패스워드 입력
    * */
    @NotBlank
    private String password;

    public PostingForm(String title, String content, String writer, String password) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
