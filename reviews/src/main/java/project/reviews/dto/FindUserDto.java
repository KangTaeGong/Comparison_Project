package project.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* 2022-09-16 생성
* 패스워드를 제외한 회원의 정보를 가져오기 위한 DTO
* */
@Getter
@AllArgsConstructor
public class FindUserDto {

    private String userId;
    private String userName;
    private String password;
}
