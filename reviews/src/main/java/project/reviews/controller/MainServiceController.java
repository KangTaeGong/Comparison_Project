package project.reviews.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import project.reviews.domain.User;
import project.reviews.dto.MainServiceDto;
import project.reviews.login.SessionConst;
import project.reviews.service.MainService;
import project.reviews.service.RecordService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


/*
 * 2022-11-09
 * 메인 서비스(검색시 정보 출력 및 리뷰 비교) Controller
 * */
@Controller
@RequiredArgsConstructor
public class MainServiceController {

    private final MainService mainService;
    private final RecordService recordService;

    /*
    * AutoSearch 기능 관련 메소드
    * 사용자가 입력한 값에 따른 결과값(autoSearch 기능)을 화면단에 보여주는 메소드
    * */
    @GetMapping(value = "/autoSearch", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public void searchItem(HttpServletRequest request, HttpServletResponse response) {

        String searchItem = request.getParameter("searchValue");

        try{
            JSONArray jsonArray = mainService.autoSearchService(searchItem);

            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.print(jsonArray);
            pw.flush();
            pw.close();

        } catch (IOException e) {
            // encoding error
            e.printStackTrace();
        }
    }

    /*
    * 메인화면에서 넘겨준 영화의 제목이 하나인지 둘인지 먼저 확인
    * 개수에 따라 알맞은 화면 출력
    * */
    @GetMapping("/mainService")
    public String mainService(String searchItem1, String searchItem2, String itemLink1, String itemLink2, Model model,
                              HttpServletRequest request) {

        String error; // MainService에서 넘겨준 에러 링크 확인용

        User loginUser = LoginSessionCheck.check_loginUser(request);

        if(searchItem1.equals("") && searchItem2.equals("")) {
            return "error/notFoundError";
        } else if (!searchItem1.equals("") && !searchItem2.equals("")) {
            // 두개의 검색어로 검색했을 시 동작
            return compare_movies(searchItem1, searchItem2, itemLink1, itemLink2, model, request, loginUser);
        }

        /*
        * 하나의 검색어만 입력할 시 동작
        * searchItem1, searchItem2 중 어디로 들어올지 모르기 때문에 하나로 합쳐줌(itemLink1, itemLink2도 마찬가지)
        * 여기서 searchItem은 값이 무조건 들어가지만, itemLink는 autoSearch 사용 여부에 따라 값이 들어가지 않을 수도 있다.
        * */
        return offer_movieInfo(searchItem1, searchItem2, itemLink1, itemLink2, model, request, loginUser);
    }

    private String offer_movieInfo(String searchItem1, String searchItem2, String itemLink1, String itemLink2, Model model, HttpServletRequest request, User loginUser) {
        String error;
        String searchItem, itemLink;
        if(!searchItem1.equals("")) {
            searchItem = searchItem1;
            itemLink = itemLink1;
        } else {
            searchItem = searchItem2;
            itemLink = itemLink2;
        }

        Map<String, Object> movieInfo = mainService.movieSearchService(searchItem, itemLink);// 영화 정보 검색
        itemLink = String.valueOf(movieInfo.get("link"));
        error = check_serviceError(itemLink); // 검색된 결과에서 정상적인 link값이 들어있지 않고, MainService에서 넘겨준 에러 메시지가 담겨 있다면 error 페이지로 리턴
        if (error != null) return error;

        MainServiceDto mainServiceDto = mainService.reviewCrawlLogic(itemLink); // 리뷰 정보를 크롤링 후 DTO에 넣어준 결과를 받음

        /*
         * 검색한 영화의 제목과 사용자 정보를 같이 저장
         * 회원 정보 조회시 최근 검색어에서 사용
         * */
        if (loginUser != null) {
            recordService.saveMovie(searchItem, loginUser);
        }

        LoginSessionCheck.check_loginUser(request, model);

        model.addAttribute("movie", movieInfo);
        model.addAttribute("mainServiceDto", mainServiceDto);

        return "service/servicePage";
    }

    private String compare_movies(String searchItem1, String searchItem2, String itemLink1, String itemLink2, Model model, HttpServletRequest request, User loginUser) {
        String error;
        Map<String, Object> movieInfo1 = mainService.movieSearchService(searchItem1, itemLink1);
        Map<String, Object> movieInfo2 = mainService.movieSearchService(searchItem2, itemLink2);

        itemLink1 = String.valueOf(movieInfo1.get("link"));

        // 검색된 결과에서 정상적인 link값이 들어있지 않고, MainService에서 넘겨준 에러 메시지가 담겨 있다면 error 페이지로 리턴
        error = check_serviceError(itemLink1);
        if (error != null) return error;

        itemLink2 = String.valueOf(movieInfo2.get("link"));

        // 검색된 결과에서 정상적인 link값이 들어있지 않고, MainService에서 넘겨준 에러 메시지가 담겨 있다면 error 페이지로 리턴
        error = check_serviceError(itemLink2);
        if (error != null) return error;

        MainServiceDto movieInfoDto1 = mainService.reviewCrawlLogic(itemLink1);
        MainServiceDto movieInfoDto2 = mainService.reviewCrawlLogic(itemLink2);

        /*
        * 검색한 영화의 제목과 사용자 정보를 같이 저장
        * 회원 정보 조회시 최근 검색어에서 사용
        * */
        if (loginUser != null) {
            String searchItem = (searchItem1 + ", " + searchItem2); // 두 개인 검색 결과를 하나의 문자열로 합쳐서 저장
            recordService.saveMovie(searchItem, loginUser);
        }

        LoginSessionCheck.check_loginUser(request, model);

        model.addAttribute("movieInfo1", movieInfo1);
        model.addAttribute("movieInfo2", movieInfo2);
        model.addAttribute("movieInfoDto1", movieInfoDto1);
        model.addAttribute("movieInfoDto2", movieInfoDto2);

        return "service/compareServicePage";
    }

    // MainService에서 넘겨준 에러를 확인
    private String check_serviceError(String itemLink) {
        if(itemLink.equals("notFoundError"))
            return "error/notFoundError";
        else if(itemLink.equals("tooManyResultsError"))
            return "error/tooManyResultsError";
        return null;
    }
}