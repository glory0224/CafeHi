package com.cafeHi.www.mapper.member;

import org.apache.ibatis.annotations.Mapper;

import com.cafeHi.www.member.dto.Membership;

@Mapper
public interface MembershipMapper {
	
	void insertMembership(Membership membership);
	
	Membership getMembership(Long member_code);

	public int updateMembershipPoint(Membership membership);
	
}
