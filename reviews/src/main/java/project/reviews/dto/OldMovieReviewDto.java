package project.reviews.dto;

import lombok.Getter;

/*
* 2022-12-06
* 구 버전 기자, 평론가 리뷰 정보를 저장하기 위한 DTO
* */
@Getter
public class OldMovieReviewDto {

    private String score;
    private String reple_text;
    private String name;

    public OldMovieReviewDto(String score, String reple_text, String name) {
        this.score = score;
        this.reple_text = reple_text;
        this.name = name;
    }
}
