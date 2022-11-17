package project.reviews.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import project.reviews.api.NaverApiClientInfo;
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
    @GetMapping(value = "/infoService", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public void searchItem(HttpServletRequest request, HttpServletResponse response) {

        String searchItem = request.getParameter("searchValue");
        log.info("searchValue = {}", searchItem);
        try{
            String url = URLEncoder.encode(searchItem,"UTF-8");
            String result = naverMovieApi.search(NaverApiClientInfo.client_id, NaverApiClientInfo.client_secret, url);

            // 가져오고 싶은 정보
            String[] fields = {"title", "image", "pubDate", "director", "actor", "userRating"};
            Map<String, Object> mappingResult = naverMovieApi.getResultMapping(result, fields);

            List<Map<String, Object>> items = (List<Map<String, Object>>) mappingResult.get("result");

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = null;
            // 메인화면에서 검색시 autocomplete에서 사용할 값 가공
            for(Map<String, Object> item : items) {
                jsonObject = new JSONObject();

                /*
                * 제목 값만 가져온 뒤 필요하지 않은 문자 제거
                * 제목과 일치한 부분은 강조(<b>, </b>)표시가 같이 출력되기 때문에 제거
                * */
                String title = (String) item.get("title");
                title = title.replaceAll("<b>", "");
                title = title.replaceAll("</b>", "");

                // 수정된 제목과 이미지를 받아와서 jsonObject에 넣은 뒤 jsonArray에 추가
                jsonObject.put("title", title);
                jsonObject.put("img", item.get("image"));

                jsonArray.add(jsonObject);
                log.info("JsonArray = {}", jsonArray);
            }

            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.print(jsonArray);
            pw.flush();
            pw.close();

        } catch (UnsupportedEncodingException e) {
            // encoding error
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
