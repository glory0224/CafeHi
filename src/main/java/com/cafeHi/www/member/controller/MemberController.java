package com.cafeHi.www.member.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.cafeHi.www.mapper.member.MemberMapper;
import com.cafeHi.www.member.dto.CustomUser;
import com.cafeHi.www.member.dto.Member;
import com.cafeHi.www.member.dto.MemberAuth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberMapper memberMapper;
		
	private final BCryptPasswordEncoder pwdEncoder;
	
	// 회원 수정
	
		@PostMapping("/updateMemberName")
		public String updateUserName(Member member) {
			log.info("Controller!");
			log.info("member_code : {} ", member.getMember_code());
			log.info("member_name : {} ", member.getMember_name());
			
			
			// db 정보 변경
			int result = memberMapper.updateMemberName(member);
			
			log.info("result : {}", result);
			
			// spring security session 정보 변경 
			 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 CustomUser userInfo = (CustomUser)principal;
			 userInfo.getMember().setMember_name(member.getMember_name());
			
			return "member/cafehi_memberInfo";
			
		}
		
		@PostMapping("/updateMemberContact")
		public String updateUserContact(Member member) {
			// db 정보 변경 
			memberMapper.updateMemberContact(member);
			
			// spring security session 정보 변경 
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			CustomUser userInfo = (CustomUser) principal;
			userInfo.getMember().setMember_contact(member.getMember_contact());
			
			
			return "member/cafehi_memberInfo";
		}
		
		
		@PostMapping("/updateMemberEmail")
		public String updateUserEmail(Member member) {
			
			// db 정보 변경 
			memberMapper.updateMemberEmail(member);
			
			// spring security session 정보 변경 
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			CustomUser userInfo = (CustomUser) principal;
			userInfo.getMember().setMember_email(member.getMember_email());

			
			return "member/cafehi_memberInfo";
		}
		
		@PostMapping("/updateMemberAddress")
		public String updateUserAddress(Member member) {
			
			// db 정보 변경 
			memberMapper.updateMemberAddress(member);
			
			// spring security session 정보 변경 
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			CustomUser userInfo = (CustomUser) principal;
			userInfo.getMember().setMember_road_address(member.getMember_road_address());
			userInfo.getMember().setMember_jibun_address(member.getMember_jibun_address());

			
			
			return "member/cafehi_memberInfo";
		}
		
		@PostMapping("/updateMemberDetailAddress")
		public String updateUserDetailAddress(Member member) {
			memberMapper.updateMemberDetailAddress(member);
			
			// spring security session 정보 변경 
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			CustomUser userInfo = (CustomUser) principal;
			userInfo.getMember().setMember_detail_address(member.getMember_detail_address());
			
			
			return "member/cafehi_memberInfo";
		}
		
		
		@PostMapping("/deleteMember")
		public String deleteMember(Member member, HttpSession session, HttpServletRequest request) {
			
			String MemberId = member.getMember_id();
			String MemberPw = member.getMember_pw();


			//spring security session 정보 변경
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			CustomUser userInfo = (CustomUser) principal;
			
			String securityId = userInfo.getUsername();
			String securityPw = userInfo.getMember().getMember_pw();
			

			// 입력받은 계정 정보와 세션 정보 비교
			if(MemberId.equals(securityId) && pwdEncoder.matches(MemberPw, securityPw)) {

				int member_code = userInfo.getMember().getMember_code();
				memberMapper.deleteMember(member_code);

				session.invalidate(); // 세션 정보 삭제
				SecurityContextHolder.getContext().setAuthentication(null); // 세션 권한 정보 null 초기화 
								
				  request.setAttribute("msg", "삭제가 완료되었습니다!"); 
				  request.setAttribute("url", "/");


			} else {
			
				request.setAttribute("msg", "아이디 또는 비밀번호를 확인해주세요!");
				request.setAttribute("url", "/MemberInfoDelete");

			}


			return "common/alert";


		}
		
	
}
