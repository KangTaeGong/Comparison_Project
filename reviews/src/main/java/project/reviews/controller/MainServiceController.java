package project.reviews.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import project.reviews.dto.MainServiceDto;
import project.reviews.service.MainService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


/*
 * 2022-11-09
 * 메인 서비스(검색시 정보와 출력 및 리뷰 비교) Controller
 * */
@Slf4j
@Controller
@RequiredArgsConstructor
public class MainServiceController {

    private final MainService mainService;

    /*
    * 검색어 입력에 따라 사용자가 원하는 정보를 가져오는 로직
    * NaverMovieApi의 search 메소드를 통해서 정보 검색
    * readBody를 통해서 검색된 정보를 모두 가져온다.
    * getResultMapping을 통해서 그중 원하는 정보만(fields) Map의 key를 통해서 가져온다.
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
    * 확인되면 개수에 따라 필요한 화면 출력
    * */
    @GetMapping("/mainService")
    public String mainService(String searchItem1, String searchItem2, String itemLink1, String itemLink2, Model model) {

        if(searchItem1.equals("") && searchItem2.equals("")) {
            // 오류페이지 리턴 필요(검색어가 없을 경우) --------------------------+
        } else if (!searchItem1.equals("") && !searchItem2.equals("")) {
            // 두개의 검색어로 검색했을 시 동작
            Map<String, Object> movieInfo1 = mainService.movieSearchService(searchItem1, itemLink1);
            Map<String, Object> movieInfo2 = mainService.movieSearchService(searchItem2, itemLink2);

            itemLink1 = String.valueOf(movieInfo1.get("link"));
            itemLink2 = String.valueOf(movieInfo2.get("link"));

            MainServiceDto movieInfoDto1 = mainService.reviewCrawlLogic(itemLink1);
            MainServiceDto movieInfoDto2 = mainService.reviewCrawlLogic(itemLink2);

            model.addAttribute("movieInfo1", movieInfo1);
            model.addAttribute("movieInfo2", movieInfo2);
            model.addAttribute("movieInfoDto1", movieInfoDto1);
            model.addAttribute("movieInfoDto2", movieInfoDto2);

            return "service/compareServicePage";
        }

        /*
        * 위 if문을 지나서 그밖에 조건
        * 하나의 검색어만 입력할 시 동작
        * searchItem1, searchItem2 중 어디로 들어올지 모르기 때문에 하나로 합쳐줌
        * itemLink1, itemLink2도 마찬가지..
        * 여기서 searchItem은 무조건 값이 들어가지만, itemLink는 autoSearch 사용 여부에 따라 값이 들어가지 않을 수도 있다.
        * */
        String searchItem, itemLink;
        if(!searchItem1.equals("")) {
            searchItem = searchItem1;
            itemLink = itemLink1;
        } else {
            searchItem = searchItem2;
            itemLink = itemLink2;
        }

        Map<String, Object> movieInfo = mainService.movieSearchService(searchItem, itemLink);// 영화 검색 후 정보를 model에 넣는 로직
        itemLink = String.valueOf(movieInfo.get("link"));
        MainServiceDto mainServiceDto = mainService.reviewCrawlLogic(itemLink); // 리뷰 정보를 크롤링 후 DTO에 넣어준 결과를 받음

        model.addAttribute("movie", movieInfo);
        model.addAttribute("mainServiceDto", mainServiceDto);

        return "service/servicePage";
    }
}