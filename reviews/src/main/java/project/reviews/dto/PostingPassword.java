package project.reviews.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

/*
* 2022-10-12
* 게시글 수정/삭제 시 패스워드 확인을 위해 입력받는 용도
* @GetMapping("/{postingId}/read")
* */
@Getter
public class PostingPassword {

    /*
     * 수정, 삭제를 위한 패스워드 입력
     * */
    @NotBlank
    private String password;

    public PostingPassword(String password) {
        this.password = password;
    }
}
