package com.cafeHi.www.mapper.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cafeHi.www.member.dto.Member;

@Mapper
public interface MemberMapper {
	
	public int insertMember(Member member);
	
	public int updateMemberName(Member member);
	public void updateMemberContact(Member member);
	public void updateMemberEmail(Member member);
	public void updateMemberAddress(Member member);
	public void updateMemberDetailAddress(Member member);
	
	public int deleteMember(int member_code);
	
	public Member getMember(int member_code);

	public Member findMemberById(String member_id);

	public int idCheck(String member_id);

	public int checkEmail(String member_email);
	

	 
	
	
}
