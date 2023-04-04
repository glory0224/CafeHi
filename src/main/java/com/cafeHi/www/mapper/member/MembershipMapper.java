package com.cafeHi.www.mapper.member;

import com.cafeHi.www.member.dto.MembershipDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MembershipMapper {
	
	void insertMembership(MembershipDTO membershipDTO);
	
	MembershipDTO getMembership(Long member_code);

	public int updateMembershipPoint(MembershipDTO membershipDTO);
	
}
