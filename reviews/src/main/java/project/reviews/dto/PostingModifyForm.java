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

    @NotBlank
    private String content;

    public PostingModifyForm(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
