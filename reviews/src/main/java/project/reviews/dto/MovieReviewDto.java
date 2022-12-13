package project.reviews.dto;

import lombok.Getter;

/*
* 2022-12-03
* 네이버 영화 사이트에서 한줄평을 받아오기 위한 DTO
* */
@Getter
public class MovieReviewDto {
    private String score;
    private String reple_text;

    public MovieReviewDto(String score, String reple_text) {
        this.score = score;
        this.reple_text = reple_text;
    }
}
