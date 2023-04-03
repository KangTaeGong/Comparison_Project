package project.reviews.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

/*
* 2022-10-12
* 게시글 수정/삭제, 회원 삭제 등 패스워드 확인을 위해 입력받는 용도
* @GetMapping("/{postingId}/read")
* */
@Getter
public class CheckPasswordDto {

    @NotBlank
    private String password;

    public CheckPasswordDto(String password) {
        this.password = password;
    }
}
