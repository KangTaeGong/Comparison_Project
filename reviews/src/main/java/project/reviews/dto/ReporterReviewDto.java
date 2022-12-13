package project.reviews.dto;

import lombok.Getter;

/*
* 2022-12-06
* 기자, 평론가 리뷰 정보를(사진 정보 미포함) 저장하기 위한 DTO
* */
@Getter
public class ReporterReviewDto {

    private String score;
    private String reple_text;
    private String name;

    public ReporterReviewDto(String score, String reple_text, String name) {
        this.score = score;
        this.reple_text = reple_text;
        this.name = name;
    }
}
