package com.cafeHi.www.member.controller;

import com.cafeHi.www.member.dto.CustomMember;
import com.cafeHi.www.member.dto.MemberInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginView(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception, Model model) {

            model.addAttribute("error", error);
            model.addAttribute("exception", exception);

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

    // 인가 권한 처리 - 부여된 권한이 아닌 경우 denied 페이지로 이동
    @GetMapping("/denied")
    public String accessDenied(@RequestParam(value = "exception", required = false) String exception, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
        model.addAttribute("username", memberInfo.getMemberName());
        model.addAttribute("exception", exception);

        return "common/denied";
    }
}
