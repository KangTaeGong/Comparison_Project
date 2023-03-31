package project.reviews.dto;

import lombok.Getter;
import java.util.List;

/*
* 2022-12-07
* 네이버 영화 API를 통해 가져온 정보들을 넣는 DTO
* */
@Getter
public class MainServiceDto {
    private String reviewLink; // 평점 링크
    private List<MovieReviewDto> reviewList; // 한줄평 정보
    private String reporter_html; // 기자, 평론가 리뷰 정보(사진 포함)
    private List<ReporterReviewDto> noPic_reporterList; // 기자, 평론가 리뷰 정보(사진 미포함)

    public MainServiceDto(String reviewLink, List<MovieReviewDto> reviewList, String reporter_html, List<ReporterReviewDto> noPic_reporterList) {
        this.reviewLink = reviewLink;
        this.reviewList = reviewList;
        this.reporter_html = reporter_html;
        this.noPic_reporterList = noPic_reporterList;
    }
}