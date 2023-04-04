package com.cafeHi.www.mapper.member;

import com.cafeHi.www.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	
	 Long insertMember(MemberDTO memberDTO);
	
	 void updateMemberName(MemberDTO memberDTO);
	 void updateMemberContact(MemberDTO memberDTO);
	 void updateMemberEmail(MemberDTO memberDTO);
	 void updateMemberAddress(MemberDTO memberDTO);
	 void updateMemberDetailAddress(MemberDTO memberDTO);
	
	 int deleteMember(Long member_code);
	
	 MemberDTO getMember(Long member_code);

	 MemberDTO findMemberById(String member_id);

	 int idCheck(String member_id);

	 int checkEmail(String member_email);
	

	 
	
	
}
