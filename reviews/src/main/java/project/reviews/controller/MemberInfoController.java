package project.reviews.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.reviews.domain.Message;
import project.reviews.domain.User;
import project.reviews.dto.CheckPasswordDto;
import project.reviews.dto.RecordPostingDto;
import project.reviews.service.RecordService;
import project.reviews.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private final UserService userService;

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
    public String deletePopup(@ModelAttribute("checkPassword")CheckPasswordDto dto) {
        return "alert/deleteMember_popUp_Page";
    }

    /*
    * 팝업창에서 진행하는 회원탈퇴 처리 로직
    * */
    @PostMapping("/withdrawal_member")
    public String membership_withdrawal(@ModelAttribute("checkPassword") CheckPasswordDto passwordDto, HttpServletRequest request,
                                        BindingResult bindingResult, Model model) {
        User session_user = LoginSessionCheck.check_loginUser(request);

        /*
        * 회원 탈퇴 로직
        * 성공하면 true
        * 실패하면 false
        * */
        Boolean check_password = userService.membership_withdrawal_pass(session_user, passwordDto.getPassword());

        if (check_password) {
            HttpSession session = request.getSession(false);
            session.invalidate(); // 세션의 정보를 모두 삭제(로그인 회원 정보)
            
            model.addAttribute("data", new Message("회원 탈퇴가 완료되었습니다.", "/"));
            return "alert/message";
        }

        bindingResult.reject("withdrawFail", "비밀번호가 일치하지 않습니다.");
        model.addAttribute("checkPassword", passwordDto);
        return "alert/deleteMember_popUp_Page";
    }
}
