package project.reviews.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

/*
 * 2022-10-11
 * 글 수정, 삭제시 사용
 * 게시판 수정 폼에서 받을 데이터
 * */
@Getter
public class PostingModifyForm {

    private Long id;
    private String title;
    private String writer;
    @NotBlank
    private String content;

    /*
     * 수정, 삭제를 위한 패스워드 입력
     * */
    @NotBlank
    private String password;

    public PostingModifyForm(Long id, String title, String writer, String content, String password) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.password = password;
    }
}
