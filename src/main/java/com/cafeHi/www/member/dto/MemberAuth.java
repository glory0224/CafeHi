package com.cafeHi.www.member.dto;

import java.time.LocalDateTime;

import com.cafeHi.www.member.MemberAuthName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class MemberAuth {
	
	private int member_auth_code; // 사용자 정보 권한 코드
	private int member_code;	// 사용자 코드
	private String member_auth; // 스프링 시큐리티 사용자 권한 : ROLE_USER, ROLE_ADMIN ...
	private LocalDateTime member_auth_writetime; // 사용자 정보 권한 등록일
	private LocalDateTime member_auth_updatetime; // 사용자 정보 권한 수정일
	
	
	// 멤버 권한 설정 메서드
	public void setMemberAuthInfo(int member_code) {
		this.member_code = member_code;
		this.member_auth = MemberAuthName.ROLE_USER.toString();
		this.member_auth_writetime = LocalDateTime.now();
		this.member_auth_updatetime = LocalDateTime.now();
	}
	
	// 멤버 권한 수정 메서드
	public void updateMemberAuthTime(String memberAuthName) {
		this.member_auth = memberAuthName;
		this.member_auth_updatetime = LocalDateTime.now();
	}

	public void setMember_auth(String member_auth) {
		this.member_auth = member_auth;
	}
}
