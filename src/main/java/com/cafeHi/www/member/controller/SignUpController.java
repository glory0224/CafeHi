package com.cafeHi.www.member.controller;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.cafeHi.www.common.CommonUtils;
import com.cafeHi.www.member.service.MemberService;
import com.cafeHi.www.member.service.MembershipService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafeHi.www.member.dto.MemberDTO;
import com.cafeHi.www.member.dto.MemberAuthDTO;
import com.cafeHi.www.member.dto.MembershipDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SignUpController {

	private final MemberService memberService;
	
	private final MembershipService membershipService;
	
	private final JavaMailSender mailSender;
	
	private final BCryptPasswordEncoder pwdEncoder;
	
	
	@GetMapping("/signup")
	public String signUpView() {
		
		return "common/signup";
	}
	
	@PostMapping("/signup")
	public String signUp(MemberDTO memberDTO, MemberAuthDTO memberAuthDTO, MembershipDTO membershipDTO, HttpServletRequest request) {

		if (CommonUtils.isEmpty(memberDTO.getMember_id())
			|| CommonUtils.isEmpty(memberDTO.getMember_name())
			|| CommonUtils.isEmpty(memberDTO.getMember_pw())
			|| CommonUtils.isEmpty(memberDTO.getMember_contact())
			|| CommonUtils.isEmpty(memberDTO.getMember_email())
			|| CommonUtils.isEmpty(memberDTO.getMember_detail_address())
		) {

			request.setAttribute("msg", "가입 항목이 제대로 입력되지 않았습니다.");

			return "common/goBackAlert";
		}

		String encodepw = pwdEncoder.encode(memberDTO.getMember_pw());
		memberDTO.setMember_pw(encodepw);
		// '-'을 입력한 정보일 경우
		if(memberDTO.getMember_contact().contains("-")) {
			String[] nums = memberDTO.getMember_contact().split("-");
			String join_nums = String.join("", nums);
			memberDTO.setMember_contact(join_nums);
		}

		// 등록 및 수정 날짜 초기화 메서드
		memberDTO.setMemberDateTime();

		// 멤버 정보 등록

		memberService.insertMember(memberDTO);

		Long member_code = memberDTO.getMember_code(); // MyBatis 기본키 반환 방식 이용

		// 멤버 권한 생성

		memberAuthDTO.setMemberAuthInfo(member_code);

		// 멤버 권한 등록

		memberService.insertMemberAuth(memberAuthDTO);

		// 멤버쉽 생성

		membershipDTO.createMembership(member_code);

		membershipService.insertMembership(membershipDTO);

		return "redirect:/login";
				
		
	}
	
	// 아이디 중복체크
	@PostMapping("/IdCheck")
	public @ResponseBody int IdCheck(String member_id) {
		
		int result = memberService.idCheck(member_id);
		return result;
	}
	
	// 이메일 중복 체크 
	@PostMapping("/EmailCheck")
	public @ResponseBody int EmailCheck(String member_email) {

		int result = memberService.checkEmail(member_email);

		return result;
	}
	
	
	// 이메일 인증
	@GetMapping("/EmailAuth")
	@ResponseBody
	public String mailAuth(String email) {

		
		/* 인증번호(난수) 생성 */
		Random random = new Random();
		// 111111 ~ 999999 범위의 숫자를 얻기 위해서 nextInt(888888) + 111111
		int checkNum = random.nextInt(888888) + 111111;


		/* 이메일 보내기 */
		
		String setFrom = "CafeHi1004@gmail.com";
		String toMail = email;
		String title = "** 회원 가입 인증 이메일입니다 **";
		String content = 
						"홈페이지를 방문해주서서 감사합니다." +
					    "<br><br>" +
					    "인증 번호는 " + checkNum + "입니다." +
					    "<br>" + 
					    "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";

		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content, true);
			mailSender.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		String num = Integer.toString(checkNum);
		
		return num;
		
	}
	
	
	
	
	
}
