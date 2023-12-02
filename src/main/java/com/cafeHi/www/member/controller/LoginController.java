package com.cafeHi.www.member.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginView(Model model, String error) {

        if (error != null) {
            model.addAttribute("msg", "아이디 또는 비밀번호를 확인해주세요!");
            model.addAttribute("url", "login");
            return "common/alert"; }

        return "common/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // 인증 객체를 얻어옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 객체가 있는 경우 로그아웃 처리
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/login";
    }
}
