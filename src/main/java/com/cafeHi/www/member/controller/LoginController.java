package com.cafeHi.www.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	
	@GetMapping("/login")
	public String loginView(Model model, String error, String logout) {
		
		
		  log.info("error : {} " , error); log.info("logout : {} ", logout);
		  
		  if (error != null) { model.addAttribute("msg", "아이디 또는 비밀번호를 확인해주세요!");
		  model.addAttribute("url", "login"); 
		  return "common/alert"; }
		
		return "common/login";
	}
	
	


}
