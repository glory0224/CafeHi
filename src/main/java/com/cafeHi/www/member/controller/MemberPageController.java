package com.cafeHi.www.member.controller;

import com.cafeHi.www.member.dto.MemberForm;
import com.cafeHi.www.member.dto.MemberInfo;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberPageController {

    private final MemberService memberService;

    @GetMapping("/CafeHi-MemberMyPage")
    public String MemberMyPageView() {
        return "member/cafehi_myPage";
    }

    @GetMapping("/CafeHi-MemberInfo")
    public String MemberInfoView(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberInfo memberInfo = (MemberInfo) principal;

        Member findMember = memberService.findMember(memberInfo.getMemberCode());

        MemberForm memberForm = new MemberForm();

        memberForm.setId(findMember.getId());
        memberForm.setMemberId(findMember.getMemberId());
        memberForm.setMemberName(findMember.getMemberName());
        memberForm.setMemberContact(findMember.getMemberContact());
        memberForm.setMemberEmail(findMember.getMemberEmail());
        memberForm.setMemberRoadAddress(findMember.getMemberRoadAddress());
        memberForm.setMemberJibunAddress(findMember.getMemberJibunAddress());
        memberForm.setMemberDetailAddress(findMember.getMemberDetailAddress());

        model.addAttribute("memberForm", memberForm);

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
//        CustomUser memberInfo = (CustomUser) principal;
        MemberInfo memberInfo = (MemberInfo) principal;
        model.addAttribute("MembershipGrade", memberInfo.getMembership().getMembershipGrade());
        model.addAttribute("MemebershipPoint", memberInfo.getMembership().getMembershipPoint());

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