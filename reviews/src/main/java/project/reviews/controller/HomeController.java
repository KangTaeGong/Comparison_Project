package project.reviews.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.reviews.domain.User;
import project.reviews.dto.RecordPostingDto;
import project.reviews.service.RecordService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final RecordService recordService;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {

        /*
        * 세션에 존재하는 회원 정보가 있다면 받아온 후 model을 통해 값을 넘거줌
        * null이면 로그인이 되지 않은 상태로 확인.
        * */
        LoginSessionCheck.check_loginUser(request, model);

        return "main/mainPage";
    }

    @GetMapping("/aboutPage")
    public String aboutPage() {
        return "main/aboutPage";
    }

    @GetMapping("/servicePage")
    public String servicePage() {
        return "main/servicePage";
    }

    /*
    * 회원 정보 화면
    * 넘겨주는 model 값
    *   - Session에서 가져온 회원 정보
    *   - 사용자가 검색한 영화 제목 List
    *   - 사용자가 작성한 게시글 List
    * */
    @GetMapping("/memberInfo")
    public String memberInfoPage(Model model, HttpServletRequest request) {

        User session_user = LoginSessionCheck.check_loginUser(request);

        List<String> movieList = recordService.getMovieList(session_user);
        List<RecordPostingDto> postingList = recordService.getPostingList(session_user);

        model.addAttribute("user", session_user);
        model.addAttribute("movies", movieList);
        model.addAttribute("postings", postingList);

        return "main/memberInfoPage";
    }
}