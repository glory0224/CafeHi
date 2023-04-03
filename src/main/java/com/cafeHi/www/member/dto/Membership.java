package com.cafeHi.www.member.dto;

import java.time.LocalDateTime;

import com.cafeHi.www.member.MembershipGrade;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Membership {
	
	private Long membership_code;
	private Long member_code; // 멤버 정보
	private String membership_grade;
	private int membership_point;
	private double membership_new_point;
	private LocalDateTime membership_writetime;
	private LocalDateTime membership_updatetime;
	
	
	// 회원 멤버쉽 생성
	public void createMembership(Long member_code) {
		this.member_code = member_code;
		this.membership_grade = MembershipGrade.STANDARD.toString();
		this.membership_point = 0;
		this.membership_writetime = LocalDateTime.now();
		this.membership_updatetime = LocalDateTime.now();
	}

	public void setMembershipPointInfo(Long member_code, int membership_point, double membership_new_point) {
		this.member_code = member_code;
		this.membership_point = membership_point;
		this.membership_new_point = membership_new_point;
	}
	
	// 회원 멤버쉽 정보 수정 메서드 
	public void updateMembershipInfo(String membershipGradeName, double totalPoint) {
		this.membership_grade = membershipGradeName;
		this.membership_point = (int)totalPoint;
		this.membership_updatetime = LocalDateTime.now();
	}

}



