package com.cafeHi.www.common.controller;

import com.cafeHi.www.member.dto.MemberForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
	
	@GetMapping("/")
	public String home() {
		return "common/main";
	}
	
	@GetMapping("/CafeHi-Introduce")
	public String Introduce() {
		
		return "common/cafehi_introduce";
		
	}
	
	@GetMapping("/CafeHi-Membership")
	public String Membership() {
		return "common/cafehi_membership";
		
	}
	
	@GetMapping("/CafeHi-Event")
	public String Event() {
		return "common/cafehi_event";
		
	}
	
	@GetMapping("/CafeHi-Place")
	public String Place() {
		return "common/cafehi_place";
		
	}


	@GetMapping("/CafeHi-Menu")
	public String Menu() {
		
		return "common/cafehi_menu";
	}

	@GetMapping("/CafeHi-Signup")
	public String signupForm(Model model){
		model.addAttribute("memberForm", new MemberForm());
		return "common/signup";
	}

	@GetMapping("CafeHi-FindMemberId")
	public String FindMemberIdView() {
		return "common/cafehi_findMemberId";
	}

	@GetMapping("CafeHi-FindMemberPw")
	public String FindMemberPw() {
		return "common/cafehi_findMemberPw";
	}
	
	
}
