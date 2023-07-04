package com.cafeHi.www.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
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
	
	
	
	
}
