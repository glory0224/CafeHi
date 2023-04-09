package com.cafeHi.www.member.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cafeHi.www.common.CommonUtils;
import com.cafeHi.www.member.dto.MemberDTO;
import com.cafeHi.www.member.service.MemberService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.cafeHi.www.member.dto.CustomUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;
	
	// 회원 수정
	
		@PostMapping("/updateMemberName")
		public String updateUserName(MemberDTO memberDTO, HttpServletRequest request) {


			if (CommonUtils.isEmpty(memberDTO.getMember_name())) {

				request.setAttribute("msg", "가입 항목이 제대로 입력되지 않았습니다.");

				return "common/goBackAlert";

			}

			// db 정보 변경
		 	memberService.updateMemberName(memberDTO);

			
			// spring security session 정보 변경 
			 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 CustomUser userInfo = (CustomUser)principal;
			 userInfo.getMemberDTO().setMember_name(memberDTO.getMember_name());
			
			return "member/cafehi_memberInfo";
			
		}



	@PostMapping("/updateMemberContact")
		public String updateUserContact(MemberDTO memberDTO, HttpServletRequest request) {


		if (CommonUtils.isEmpty(memberDTO.getMember_contact())) {

			request.setAttribute("msg", "가입 항목이 제대로 입력되지 않았습니다.");

			return "common/goBackAlert";

		}

			// db 정보 변경
			memberService.updateMemberContact(memberDTO);
			
			// spring security session 정보 변경 
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			CustomUser userInfo = (CustomUser) principal;
			userInfo.getMemberDTO().setMember_contact(memberDTO.getMember_contact());
			
			
			return "member/cafehi_memberInfo";
		}
		
		
		@PostMapping("/updateMemberEmail")
		public String updateUserEmail(MemberDTO memberDTO, HttpServletRequest request) {


			if (CommonUtils.isEmpty(memberDTO.getMember_email())) {

				request.setAttribute("msg", "가입 항목이 제대로 입력되지 않았습니다.");

				return "common/goBackAlert";

			}

			// db 정보 변경 
			memberService.updateMemberEmail(memberDTO);
			
			// spring security session 정보 변경 
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			CustomUser userInfo = (CustomUser) principal;
			userInfo.getMemberDTO().setMember_email(memberDTO.getMember_email());

			
			return "member/cafehi_memberInfo";
		}
		
		@PostMapping("/updateMemberAddress")
		public String updateUserAddress(MemberDTO memberDTO, HttpServletRequest request) {


			if (CommonUtils.isEmpty(memberDTO.getMember_jibun_address())) {

				memberDTO.setMember_jibun_address("없음");

			}else if(CommonUtils.isEmpty(memberDTO.getMember_road_address())) {

				memberDTO.setMember_road_address("없음");

			}else if(CommonUtils.isEmpty(memberDTO.getMember_jibun_address()) || CommonUtils.isEmpty(memberDTO.getMember_road_address())){
				request.setAttribute("msg", "가입 항목이 제대로 입력되지 않았습니다.");
				return "common/goBackAlert";
			}

			// db 정보 변경 
			memberService.updateMemberAddress(memberDTO);
			
			// spring security session 정보 변경 
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			CustomUser userInfo = (CustomUser) principal;
			userInfo.getMemberDTO().setMember_road_address(memberDTO.getMember_road_address());
			userInfo.getMemberDTO().setMember_jibun_address(memberDTO.getMember_jibun_address());

			
			
			return "member/cafehi_memberInfo";
		}
		
		@PostMapping("/updateMemberDetailAddress")
		public String updateUserDetailAddress(MemberDTO memberDTO, HttpServletRequest request) {


			if (CommonUtils.isEmpty(memberDTO.getMember_detail_address())) {

				request.setAttribute("msg", "가입 항목이 제대로 입력되지 않았습니다.");

				return "common/goBackAlert";

			}

			memberService.updateMemberDetailAddress(memberDTO);
			
			// spring security session 정보 변경 
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			CustomUser userInfo = (CustomUser) principal;
			userInfo.getMemberDTO().setMember_detail_address(memberDTO.getMember_detail_address());
			
			
			return "member/cafehi_memberInfo";
		}
		
		
		@PostMapping("/deleteMember")
		public String deleteMember(MemberDTO memberDTO, HttpSession session, HttpServletRequest request) {
			
			String MemberId = memberDTO.getMember_id();
			String MemberPw = memberDTO.getMember_pw();


			//spring security session 정보 변경
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			CustomUser userInfo = (CustomUser) principal;
			Long member_code = userInfo.getMemberDTO().getMember_code();

			// 입력한 비밀번호와 현재 사용자의 비밀번호 일치 여부 확인
			boolean isPasswordMatched = memberService.isPasswordMatched(member_code, MemberPw);


			// 입력받은 계정 정보와 세션 정보 비교
			if(MemberId.equals(userInfo.getUsername()) && isPasswordMatched) {

				memberService.deleteMember(member_code);
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
