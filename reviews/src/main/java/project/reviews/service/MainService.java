package project.reviews.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import project.reviews.api.NaverMovieApiService;
import project.reviews.dto.MainServiceDto;
import project.reviews.dto.MovieReviewDto;
import project.reviews.dto.ReporterReviewDto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/*
* 2022-12-07
* MainServiceController 관련 비즈니스 로직
* */
@Service
@RequiredArgsConstructor
public class MainService {

    private final NaverMovieApiService naverMovieApiService;

    public JSONArray autoSearchService(String searchItem) {
        // 가져오고 싶은 정보
        String[] fields = {"title", "image", "link"};

        List<Map<String, Object>> items = getMovieApiResult(searchItem, fields);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        // 메인화면에서 검색시 autocomplete에서 사용할 값 가공
        for (Map<String, Object> item : items) {
            jsonObject = new JSONObject();

            /*
             * 제목 값만 가져온 뒤 필요하지 않은 문자 제거
             * 제목과 일치한 부분은 강조(<b>, </b>)표시가 같이 출력되기 때문에 제거
             * */
            String title = replace_Title(item);

            // 수정된 제목과 이미지를 받아와서 jsonObject에 넣은 뒤 jsonArray에 추가
            jsonObject.put("title", title);
            jsonObject.put("img", item.get("image"));
            jsonObject.put("link", item.get("link"));

            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /*
    * 사용자가 입력한 검색어에 대한 영화를 검색하는 로직
    * 반환 받은 결과에 따른 출력(정상 결과 / 에러 페이지)
    * searchItem : 사용자가 입력한 영화 제목
    * itemLink : 검색한 영화에 대한 네이버 영화 link(autoSearch 여부에 따라 없을 수도 있음)
    * */
    public Map<String, Object> movieSearchService(String searchItem, String itemLink) {
        String[] fields = {"title", "link", "image", "pubDate", "director", "actor", "userRating"};

        List<Map<String, Object>> movies = getMovieApiResult(searchItem, fields);

        /*
         * 검색어에 대한 정보를 가져온 뒤 필요한 값만 넘겨주는 작업
         * 검색시 autoSearch를 통해 정확한 검색어를 입력하지 않아도, 검색어가 하나밖에 없을 경우에는 동일하게 동작한다.
         * 검색어를 찾을 수 없거나 검색어가 많을 경우 오류 페이지 송출
         * */
        for(Map<String, Object> movie : movies) {
            if (movies.size() == 1) { 
                itemLink = String.valueOf(movie.get("link"));
                String title = replace_Title(movie); // 제목 정보를 가져올 때 <b></b>가 붙어있기 때문에 제거해주는 작업

                movie.put("title", title);

                return movie;
            } else if (!itemLink.equals("")) { // 검색 결과가 여러개가 나오지만, 링크를 가지고 있는 경우(autoSearch 사용)
                if (String.valueOf(movie.get("link")).equals(itemLink)) { // item을 통해서 가져오는 link 정보와 autoSearch를 통해 넘겨받은 link 정보를 매치
                    String title = replace_Title(movie); // 제목 정보를 가져올 때 <b></b>가 붙어있기 때문에 제거해주는 작업

                    movie.put("title", title);
                    return movie;
                }
            }
        }

        Map<String, Object> errorMap = new HashMap<>();

        // MainServiceController에서 link값을 확인하기 때문에 에러 페이지 정보를 링크에 넣어서 리턴
        if (!movies.isEmpty()) {
            errorMap.put("link", "tooManyResultsError"); // 검색된 결과가 너무 많다면 link에 메시지를 적어서 controller에 전달

            return errorMap;
        }
        
        // 모든 조건문에 걸리지 않았다면 에러 리턴
        errorMap.put("link", "notFoundError");
        return errorMap;
    }


    /*
    * 사용자가 입력한 검색어에 대해 NaverMovieApiService에서 영화를 검색하고, 그 결과를 필요한 정보만 매핑해서 리턴
    * searchItem : 사용자가 입력한 검색어
    * fields : 필요한 정보를 매핑할 때 사용할 key값
    * */
    private List<Map<String, Object>> getMovieApiResult(String searchItem, String[] fields) {

        String url;
        List<Map<String, Object>> items = null;
        try {
            url = URLEncoder.encode(searchItem, "UTF-8");
            String result = naverMovieApiService.searchNaverMovie(url);
            // 가져오고 싶은 정보 매핑
            Map<String, Object> mappingResult = naverMovieApiService.getResultMapping(result, fields);

            items = (List<Map<String, Object>>) mappingResult.get("result");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return items;
    }

    /*
     * 리뷰 화면 크롤링 작업
     * 검색에 필요한 링크(Url)를 파라미터로 필요
     * 필요한 정보를 뽑아서 Controller에 리턴 해주는 역할
     * */
    public MainServiceDto reviewCrawlLogic(String movieLink) {

        /*
         * 링크에 대한 커넥션 받기
         * 관람객 한줄평을 받아올 때 사용
         * */
        Connection basicConn = Jsoup.connect(movieLink);

        // 기본 링크에서 basic -> point로 바꿔 상세 리뷰 페이지 링크 생성
        String reviewLink = movieLink.replace("basic", "point");
        Connection reviewConn = Jsoup.connect(reviewLink);

        // 한줄평에 대한 정보들을 list에 저장 후 model로 넘겨줌
        List<MovieReviewDto> reviewList = new ArrayList<>();
        try {
            Document basicDocument = basicConn.get(); // 관람객의 한줄평 정보를 가져올 수 있음
            Document reviewDocument = reviewConn.get(); // 평론가의 리뷰 정보를 가져올 수 있음

            /*
             * 관람객의 한줄평 리뷰에 대한 정보
             * */
            Elements citizenReviewElements = basicDocument.select("div.score_reple > p"); // 관람객 한줄평 텍스트
            Elements citizenScoreElements = basicDocument.select("div.score_result > ul > li > div.star_score > em"); // 관람객 평점

            for(int i = 0; i < citizenReviewElements.size(); i++) {
                String review = citizenReviewElements.get(i).text(); // 관람객 한줄평
                String score = citizenScoreElements.get(i).text(); // 관람객이 준 점수
                MovieReviewDto reviewDto = new MovieReviewDto(score, review);
                reviewList.add(reviewDto); // 한줄평 정보를 list에 저장(추후에 model로 넘김)
            }

            /*
             * 기자, 평론가의 리뷰에 대한 정보를 html로 가져온다.
             * 사진이 없는 기자, 평론가에 대한 리뷰는 html이 다르기 때문에 둘 다 가져와서 뿌려줘야 함
             * */
            Elements reporterHtmlElements = reviewDocument.select("div.reporter"); // 기자, 평론가 리뷰(사진 포함)
            String reporterHtml = reporterHtmlElements.html(); // 기자, 평론가 관련 리뷰를 html 형식으로 저장

            // 사진 없는 기자, 평론가 리뷰
            Elements ReporterScoreElements = reviewDocument.select("div.score140 > div.score_result > ul > li > div.star_score > em"); // 점수
            Elements ReporterRepleElements = reviewDocument.select("div.score140 > div.score_result > ul > li > div.score_reple > p"); // 리뷰
            Elements ReporterNameElements = reviewDocument.select("div.score140 > div.score_result > ul > li > div.score_reple > dl"); // 이름

            List<ReporterReviewDto> noPic_reporterList = new ArrayList<>();
            for(int i = 0; i < ReporterRepleElements.size(); i++) {
                String reporter_score = ReporterScoreElements.get(i).text();
                String reporter_review = ReporterRepleElements.get(i).text();
                String reporter_name = ReporterNameElements.get(i).text();

                ReporterReviewDto reporterReviewDto = new ReporterReviewDto(reporter_score, reporter_review, reporter_name);
                noPic_reporterList.add(reporterReviewDto); // 기자, 평론가 관련 리뷰(사진 X)를 list에 저장
            }

            /*
             * return 값
             * reviewLink : 리뷰 더보기 클릭시 사용할 링크값
             * reviewList : 관람객 한줄평에 대한 정보 list
             * reporterHtml : 기자, 평론가의 리뷰에 대한 정보(사진 포함)
             * noPic_reporterList: 기자, 평론가의 리뷰에 대한 정보(사진 미포함)
             * */
            return new MainServiceDto(reviewLink, reviewList, reporterHtml, noPic_reporterList);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 검색해온 제목에서 불필요한 문자 제거 후 반환
    private String replace_Title(Map<String, Object> item) {
        String title = String.valueOf(item.get("title"));
        title = title.replaceAll("<b>", "");
        title = title.replaceAll("</b>", "");
        return title;
    }
}