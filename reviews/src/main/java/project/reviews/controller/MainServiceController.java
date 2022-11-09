package project.reviews.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import project.reviews.api.NaverApiClientInfo;
import project.reviews.api.NaverMovieApi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/*
 * 2022-11-09
 * 메인 서비스(검색시 정보와 출력 및 리뷰 비교) Controller
 * */
@Slf4j
@Controller
@RequiredArgsConstructor
public class MainServiceController {

    /*
    * 검색어 입력에 따라 사용자가 원하는 정보를 가져오는 로직
    * NaverMovieApi의 search 메소드를 통해서 정보 검색
    * readBody를 통해서 검색된 정보를 모두 가져온다.
    * getResultMapping을 통해서 그중 원하는 정보만(fields) Map의 key를 통해서 가져온다.
    * */
    @GetMapping("/infoService")
    public String searchItem(String searchItem1, String searchItem2) {

        log.info("searchItem1 = {}", searchItem1);
        log.info("searchItem2 = {}", searchItem2);

        try{
            NaverMovieApi api = new NaverMovieApi();
            String url = URLEncoder.encode(searchItem1,"UTF-8");
            String response = api.search(NaverApiClientInfo.client_id, NaverApiClientInfo.client_secret, url);

            // 가져오고 싶은 정보
            String[] fields = {"title", "image", "pubDate", "director", "actor", "userRating"};
            Map<String, Object> result = api.getResultMapping(response, fields);

            if(result.size() > 0)
                log.info("total => " + result.get("total"));

            List<Map<String, Object>> items = (List<Map<String, Object>>) result.get("result");

            for(Map<String, Object> item : items) {
                log.info("========================================");

                for(String field : fields)
                    log.info(field + "->" + item.get(field));
            }

        } catch (UnsupportedEncodingException e) {
            // encoding error
            e.printStackTrace();
        }

        if(searchItem1 != "" && searchItem2 != "") {
            return "";
        }

        return "service/servicePage";
    }
}
