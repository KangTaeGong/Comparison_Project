package project.reviews.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import project.reviews.api.NaverMovieApi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

    private final NaverMovieApi naverMovieApi;
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
        log.info("searchValue = {}", searchItem);

        // 가져오고 싶은 정보
        String[] fields = {"title", "image", "link"};
        try{
            List<Map<String, Object>> items = getApiResult(searchItem, fields);

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = null;
            // 메인화면에서 검색시 autocomplete에서 사용할 값 가공
            for(Map<String, Object> item : items) {
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
                log.info("JsonArray = {}", jsonArray);
            }

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

        log.info("searchItem1 = {}", searchItem1);
        log.info("searchItem2 = {}", searchItem2);

        log.info("searchLink1 = {}", itemLink1);
        log.info("searchLink2 = {}", itemLink2);

        String[] fields = {"title", "link", "image", "pubDate", "director", "actor", "userRating"};

        List<Map<String, Object>> items = getApiResult(searchItem1, fields);

        for(Map<String, Object> item : items) {
            if ((item.get("link")).equals(itemLink1)) {
                String title = replace_Title(item);

                item.put("title", title);
                model.addAttribute("movie", item);
                break;
            }
        }

        // 그 밖에 하나만 입력한 경우에는 정보 하나만 송출해주는 servicePage로 이동
        return "service/servicePage";
    }

    // 검색해온 제목에서 불필요한 문자 제거 후 반환
    private String replace_Title(Map<String, Object> item) {
        String title = String.valueOf(item.get("title"));
        title = title.replaceAll("<b>", "");
        title = title.replaceAll("</b>", "");
        return title;
    }

    public List<Map<String, Object>> getApiResult(String searchItem, String[] fields) {

        String url = null;
        List<Map<String, Object>> items = null;
        try {
            url = URLEncoder.encode(searchItem, "UTF-8");

            String result = naverMovieApi.search(url);

            // 가져오고 싶은 정보 매핑
            Map<String, Object> mappingResult = naverMovieApi.getResultMapping(result, fields);

            items = (List<Map<String, Object>>) mappingResult.get("result");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return items;
    }
}