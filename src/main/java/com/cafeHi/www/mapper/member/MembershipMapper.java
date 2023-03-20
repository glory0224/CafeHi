package com.cafeHi.www.mapper.member;

import org.apache.ibatis.annotations.Mapper;

import com.cafeHi.www.member.dto.Membership;

@Mapper
public interface MembershipMapper {
	
	public void insertMembership(Membership membership);
	
	public Membership getMembership(int member_code);

	public int updateMembershipPoint(Membership membership);
	
}
