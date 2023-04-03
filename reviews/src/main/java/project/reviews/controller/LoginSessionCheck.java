package project.reviews.controller;

import org.springframework.ui.Model;
import project.reviews.domain.User;
import project.reviews.login.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
 *  2022-10-21
 *  현재 로그인한 상태인지 session에서 값 받아오기
 * */
public class LoginSessionCheck {

    public static User check_loginUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (User)session.getAttribute(SessionConst.LOGIN_USER);
    }

    public static void check_loginUser(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            User sessionUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
            model.addAttribute("user", sessionUser);
        }
    }
}
