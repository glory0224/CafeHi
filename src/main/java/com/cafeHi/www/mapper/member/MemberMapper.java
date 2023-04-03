package com.cafeHi.www.mapper.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cafeHi.www.member.dto.Member;

@Mapper
public interface MemberMapper {
	
	 Long insertMember(Member member);
	
	 void updateMemberName(Member member);
	 void updateMemberContact(Member member);
	 void updateMemberEmail(Member member);
	 void updateMemberAddress(Member member);
	 void updateMemberDetailAddress(Member member);
	
	 int deleteMember(Long member_code);
	
	 Member getMember(Long member_code);

	 Member findMemberById(String member_id);

	 int idCheck(String member_id);

	 int checkEmail(String member_email);
	

	 
	
	
}
