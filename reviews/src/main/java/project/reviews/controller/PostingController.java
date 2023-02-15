package project.reviews.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.reviews.domain.Message;
import project.reviews.domain.User;
import project.reviews.dto.*;
import project.reviews.service.PostingService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/*
* 2022-10-06
* 커뮤니티 관련 컨트롤러
* */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/community")
public class PostingController {

    private final PostingService postingService;

    @GetMapping("/list")
    public String postingList(Model model, HttpServletRequest request,
                              @PageableDefault(page = 0, size = 15)Pageable pageable, String postingInfo) {

        // communityPage.html에서 name = postingInfo값을 넘겨주어 게시글 검색을 한다.
        Page<PostingResponseDto> postingList = postingService.getPosting_paging(pageable, postingInfo);
        int currentPage = postingList.getPageable().getPageNumber()+1;
        int startPage = 1;
        int endPage = postingList.getTotalPages();
//        int postingCount;

        Long totalPostingCount = postingService.getPostingCount();
        log.info("totalPostingCount = {}", totalPostingCount);

        /*if(totalPostingCount > currentPage) {
            postingCount = (int) (totalPostingCount - (currentPage * 14));
        } else {
            postingCount = 1;
        }*/

        /*
         * header.html에서 로그인 정보 유지하기 위해 작성
         * */
        LoginSessionCheck.check_loginUser(request, model);

        model.addAttribute("postingList", postingList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPostingCount", totalPostingCount); // 전체 게시글 수
//        model.addAttribute("postingCount", postingCount);   // 페이지당 게시글 No. 최소값

        return "community/communityPage";
    }

    @GetMapping("/write")
    public String writePosting_form(@ModelAttribute("postingForm") PostingForm form, Model model, HttpServletRequest request) {
        LoginSessionCheck.check_loginUser(request, model);
        return "community/communityWritePage";
    }

    @PostMapping("/write")
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
        User session_user = LoginSessionCheck.check_loginUser(request);
        String userName = session_user.getUserName();

        // PostingForm에 Session에서 가져온 작성자 입력
        form.setWriter(userName);

        Long postingId = postingService.create_posting(form, session_user.getUserId());

        // 글 생성이 완료되면 읽기 페이지로 이동
        String redirectUrl = "/community/" + postingId + "/read";
        return "redirect:" + redirectUrl;
    }

    /*
    * 게시글 읽어오기
    * */
    @GetMapping("/{postingId}/read")
    public String readPostingForm(@PathVariable("postingId") Long postingId, @ModelAttribute("postingPasswordForm") PostingPassword postingPassword, Model model
                                    ,HttpServletRequest request) {

        // postingId를 통해서 posting 조회 후 html에 posting 정보를 뿌려준다.
        PostingResponseDto posting = postingService.get_posting(postingId);
        model.addAttribute("posting", posting);

        // 게시글 읽어올 때 조회수 + 1
        postingService.update_hits(postingId);

        LoginSessionCheck.check_loginUser(request, model);
        return "community/communityReadPage";
    }

    @PostMapping("/{postingId}/read")
    public String readPosting(@PathVariable("postingId") Long postingId, @Validated @ModelAttribute("postingPasswordForm") PostingPassword postingPassword, BindingResult bindingResult
                                , Model model, HttpServletRequest request) {
        PostingResponseDto posting = postingService.get_posting(postingId);

        if(bindingResult.hasErrors()) {
            log.info("readPosting-post bindingResult() 실행");
            model.addAttribute("posting", posting);
            return "community/communityReadPage";
        }

        /*
         * 필드에 오류가 없을 시
         * getPosting_password()에 패스워드를 보내서 Service 로직에서 비교 후 결과 반환
         * */
        log.info("패스워드 확인 getPosting_password");
        PostingResponseDto check_posting = postingService.getPosting_password(postingId, postingPassword.getPassword());
        if(check_posting == null) {
            bindingResult.reject("modifyFail", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("posting", posting);
            LoginSessionCheck.check_loginUser(request, model);
            return "community/communityReadPage";
        }
        
        // 글 수정/삭제 화면으로 이동
        return "redirect:/community/" + postingId + "/modify";
    }

    /*
    * 게시글 수정, 삭제 form
    * 수정 관련 로직
    * */
    @GetMapping("/{postingId}/modify")
    public String modifyPostingForm(@PathVariable("postingId") Long postingId, Model model, HttpServletRequest request) {
        PostingResponseDto posting = postingService.get_posting(postingId);
        model.addAttribute("posting", posting);
        LoginSessionCheck.check_loginUser(request, model);
        /*
        * 글 내용만 model에 따로 담아서 전달
        * @PostMapping의 @ModelAttribute와 이름 동일
        * */
        PostingModifyForm postingModifyForm = new PostingModifyForm(postingId, posting.getContent());
        model.addAttribute("postingModifyForm", postingModifyForm);

        return "community/communityModifyPage";
    }

    @PostMapping("/{postingId}/modify")
    public String modifyPosting(@PathVariable("postingId") Long postingId, @Valid @ModelAttribute("postingModifyForm") PostingModifyForm postingModifyForm,
                                BindingResult bindingResult, Model model) {
        /*
         * 수정 시 문제 발생 시 오류 코드를 출력하고, 다시 입력
         * get_posting()을 통해서 posting정보를 가져와 model에 담아서 다시 수정 페이지로 보내준다.
         * 기존 작성되어 있던 제목, 작성자를 유지하기 위해
         * */
        if(bindingResult.hasErrors()) {
            PostingResponseDto posting = postingService.get_posting(postingId);
            log.info("modify bindingResult() 실행");
            model.addAttribute("posting", posting);
            return "community/communityModifyPage";
        }

        /*
        * 수정 성공시 결과를 DB에 반영하고 포스팅 읽기 페이지로 리다이렉트
        * PostingModifyForm에 id값을 넣어서 비즈니스 로직으로 전달
        * */
        PostingModifyForm modifyForm = new PostingModifyForm(postingId, postingModifyForm.getContent());
        postingService.update_posting(modifyForm);

        String redirectUrl = "/community/" + postingId + "/read";

        return "redirect:" + redirectUrl;
    }

    /*
    * 게시글 삭제
    * */
    @GetMapping("/{postingId}/delete")
    public String deletePosting(@PathVariable("postingId") Long postingId, Model model) {

        postingService.delete_posting(postingId);

        model.addAttribute("data", new Message("게시글이 삭제되었습니다.", "/community/list"));
        // 삭제 후 리스트 화면으로 이동
        return "alert/message";
    }
}
