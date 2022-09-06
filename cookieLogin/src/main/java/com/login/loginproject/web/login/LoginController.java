package com.login.loginproject.web.login;


import com.login.loginproject.domain.login.LoginService;
import com.login.loginproject.domain.member.Member;
import com.login.loginproject.web.login.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/logins")
    public String loginForm(@ModelAttribute("loginForm")LoginForm loginForm){
        return "/logins/loginForm";
    }

    @PostMapping("/logins")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,  HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "logins/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if(loginMember == null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "logins/loginForm";
        }

        //성공처리

        //쿠키에 시간 정보를 주지 않으면 세션쿠키(브라우저 종료시 모두 종료)
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);

        return "redirect:/";

    }

    @PostMapping("/logouts")
    public String logout(HttpServletResponse response){

        extracted(response,"memberId");
        return "redirect:/";
    }

    private void extracted(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
