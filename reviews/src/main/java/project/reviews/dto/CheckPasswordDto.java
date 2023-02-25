package project.reviews.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

/*
* 2022-10-12
* 게시글 수정/삭제 시 패스워드 확인을 위해 입력받는 용도
* @GetMapping("/{postingId}/read")
* */
@Getter
public class CheckPasswordDto {

    /*
     * 포스팅 수정, 삭제를 위한 패스워드 입력
     * 회원 탈퇴시 인증을 위한 패스워드 입력
     * */
    @NotBlank
    private String password;

    public CheckPasswordDto(String password) {
        this.password = password;
    }
}
