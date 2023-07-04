package com.cafeHi.www.member.controller;

import com.cafeHi.www.member.dto.CustomMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberPageController {

    @GetMapping("/CafeHi-MemberMyPage")
    public String MemberMyPageView() {
        return "member/cafehi_myPage";
    }

    @GetMapping("/CafeHi-MemberInfo")
    public String MemberInfoView() {

        return "member/cafehi_memberInfo";
    }

    @GetMapping("/CafeHi-MemberChangePassword")
    public String MemberChangePasswordView() {
        return "member/cafehi_memberPwChange";
    }

    @GetMapping("/CafeHi-MemberInfoDelete")
    public String MemberInfoDeleteView() {
        return "member/cafehi_memberDelete";
    }

    @GetMapping("/CafeHi-MyMembership")
    public String MyMembershipView(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomMember memberInfo = (CustomMember) principal;

        model.addAttribute("MembershipGrade", memberInfo.getMemberInfo().getMembership().getMembershipGrade());
        model.addAttribute("MemebershipPoint", memberInfo.getMemberInfo().getMembership().getMembershipPoint());

        return "member/cafehi_myMembership";
    }

    @GetMapping("/CafeHi-FindMemberId")
    public String FindMemberIdView() {
        return "common/cafehi_findMemberId";
    }

    @GetMapping("/CafeHi-FindMemberPw")
    public String FindMemberPw() {
        return "common/cafehi_findMemberPw";
    }
}