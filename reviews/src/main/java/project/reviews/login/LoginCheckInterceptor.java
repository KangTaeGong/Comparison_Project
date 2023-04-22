package project.reviews.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import project.reviews.controller.PostingController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
* 2022-10-18
* 인증된(Login된) 사용자인지 체크하는 인터셉터
* */
public class LoginCheckInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession(false);
        
        if(session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {

            /*
            * 인증이 필요한 페이지를 URL로 직접 접근시 LoginForm으로 이동
            * requestURI를 넘겨줌으로 써 로그인이 완료되면 직전에 보고 있던 화면으로 redirect
            * 게시글 수정, 삭제를 강제로 접근시 LoginForm으로 이동하고, 인증이 완료되면 게시판 list로 이동
            * */
            if(requestURI.contains("/modify") || requestURI.contains("/delete")) {
                response.sendRedirect("/login?redirectURI=" + "/community/list");
                return false;
            }
            response.sendRedirect("/login?redirectURI=" + requestURI);
            return false;
        }

        // 로그인 이후에 강제로 접속 시 list화면으로 내보냄
        if(requestURI.contains("/modify") || requestURI.contains("/delete")) {
            if (PostingController.check_posting == null) {
                response.sendRedirect("/community/list");
                return false;
            }

        }
        return true;
    }
}