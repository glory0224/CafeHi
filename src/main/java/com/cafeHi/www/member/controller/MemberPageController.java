package com.cafeHi.www.member.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cafeHi.www.member.dto.CustomUser;
import com.cafeHi.www.member.dto.Membership;
import com.cafeHi.www.member.service.MembershipServiceImpl;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MemberPageController {
	
	private final MembershipServiceImpl membershipService;
	
	
	@GetMapping("/CafeHi-MemberMyPage")
	public String MemberMyPageView() {
		return "member/cafehi_myPage";
	}
	
	@GetMapping("/CafeHi-MemberInfo")
	public String MemberInfoView() {
		return "member/cafehi_memberInfo";
	}
	
	@GetMapping("/CafeHi-MemberInfoUpdate")
	public String MemberInfoUpdateView(Model model) {
			 
		return "member/cafehi_memberUpdate";
	}
	
	@GetMapping("/CafeHi-MemberInfoDelete")
	public String MemberInfoDeleteView() {
		return "member/cafehi_memberDelete";
	}
	
	@GetMapping("/CafeHi-MyMembership")
	public String MyMembershipView(Model model) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUser userInfo = (CustomUser) principal;
		
		Long member_code = userInfo.getMember().getMember_code();


		Membership getMembership = membershipService.getMembership(member_code);
		
		model.addAttribute("MembershipGrade", getMembership.getMembership_grade());
		model.addAttribute("MemebershipPoint", getMembership.getMembership_point());
		
		return "member/cafehi_myMembership";
	}
	
	
}
