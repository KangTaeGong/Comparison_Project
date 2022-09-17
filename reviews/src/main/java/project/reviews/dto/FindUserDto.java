package project.reviews.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 2022-09-16 생성
* 패스워드를 제외한 회원의 정보를 가져오기 위한 DTO
* */
@Data
@NoArgsConstructor
public class FindUserDto {

    private String username;
    private String userId;

    public FindUserDto(String username, String userId) {
        this.username = username;
        this.userId = userId;
    }
}
