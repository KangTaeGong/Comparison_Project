package project.reviews.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.reviews.domain.User;
import project.reviews.dto.PostingForm;
import project.reviews.dto.PostingResponseDto;
import project.reviews.login.SessionConst;
import project.reviews.service.PostingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostingController {

    private final PostingService postingService;

    @GetMapping("/community/list")
    public String postingList(Model model,
                              @PageableDefault(page = 0, size = 15, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {

        Page<PostingResponseDto> postingList = postingService.getPosting_paging(pageable);

        int currentPage = postingList.getPageable().getPageNumber()+1;
        int startPage = 1;
        int endPage = postingList.getTotalPages();

        model.addAttribute("postingList", postingList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "community/communityPage";
    }

    @GetMapping("/community/write")
    public String writePosting_form(@ModelAttribute("postingForm")PostingForm form) {
        return "community/communityWritePage";
    }

    @PostMapping("/community/write")
    public String writePosting(@Valid @ModelAttribute("postingForm") PostingForm form, BindingResult bindingResult,
                               HttpServletRequest request){

        /*
        * 글 작성시에 문제가 있을 시 팝업을 띄우고, 다시 글 작성 창 띄워주기
        * */
        if(bindingResult.hasErrors()) {
            return "community/communityWritePage";
        }
        
        /*
        * 문제 없을 시 request, session을 통해서 회원 이름을 조회하고 저장 메소드로 값을 넘겨줌
        * */
        HttpSession session = request.getSession();
        User session_user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        String userName = session_user.getUserName();
        log.info("Find UserName = {}", userName);

        // PostingForm에 Session에서 가져온 작성자 입력
        form.setWriter(userName);

        postingService.create_posting(form);

        return "redirect:/community/list";
    }

    /*
    * 게시글 읽어오기
    * */
    @GetMapping("/community/{postingId}/read")
    public String readPosting(@PathVariable("postingId") Long postingId, Model model) {
        PostingResponseDto posting = postingService.get_posting(postingId);
        model.addAttribute("posting", posting);

        return "community/communityReadPage";
    }
}
