package project.reviews.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import project.reviews.api.NaverApiClientInfo;
import project.reviews.api.NaverMovieApi;
import project.reviews.domain.User;
import project.reviews.login.SessionConst;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false)
                       User loginUser, Model model) {
        /*
        * 세션에 회원 데이터가 없을 시 동작
        * 로그인이 되지 않은 메인 페이지
        * thymeleaf에 null값도 같이 넘겨주어 로그인 여부 확인
        * */
        if(loginUser == null) {
            model.addAttribute("user", loginUser);
            return "main/mainPage";
        }
        
        log.info("로그인 페이지로 이동");
        /*
        * 세션이 유지되면 로그인된 메인페이지로 이동
        * */
        log.info("Login UserName = {}", loginUser.getUserName());
        model.addAttribute("user", loginUser);
        return "main/mainPage";
    }

    /*
    * 검색어 입력에 따라 사용자가 원하는 정보를 가져오는 로직
    * NaverMovieApi의 search 메소드를 통해서 정보 검색
    * readBody를 통해서 검색된 정보를 모두 가져온다.
    * getResultMapping을 통해서 그중 원하는 정보만(fields) Map의 key를 통해서 가져온다.
    * */
    @PostMapping("/")
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
