package project.reviews.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.reviews.domain.User;
import project.reviews.dto.RecordPostingDto;
import project.reviews.service.RecordService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
* 2023-02-21
* 회원 정보창 관련 컨트롤러
* */
@Controller
@RequiredArgsConstructor
@RequestMapping("/memberInfo")
@Slf4j
public class MemberInfoController {

    private final RecordService recordService;

    /*
     * 회원 정보 화면
     * 넘겨주는 model 값
     *   - Session에서 가져온 회원 정보
     *   - 사용자가 검색한 영화 제목 List
     *   - 사용자가 작성한 게시글 List
     * */
    @GetMapping
    public String memberInfoPage(Model model, HttpServletRequest request) {

        User session_user = LoginSessionCheck.check_loginUser(request);

        List<String> movieList = recordService.getMovieList(session_user);
        List<RecordPostingDto> postingList = recordService.getPostingList(session_user);

        model.addAttribute("user", session_user);
        model.addAttribute("movies", movieList);
        model.addAttribute("postings", postingList);

        return "main/memberInfoPage";
    }

    @GetMapping("/popup")
    public String deletePopup() {

        log.info("MemberInfoController(/popup) start");
        return "alert/deleteMember_popUp_Page";
    }
}
