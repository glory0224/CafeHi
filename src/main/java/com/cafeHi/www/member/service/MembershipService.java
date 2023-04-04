package com.cafeHi.www.member.service;

import com.cafeHi.www.member.dto.MembershipDTO;

public interface MembershipService {
	
	void insertMembership(MembershipDTO membershipDTO);
	MembershipDTO getMembership(Long member_code);
	
	void updateMembershipPoint(MembershipDTO membershipDTO);

    void minusMembershipPoint(MembershipDTO getMembershipDTO, int total_order_price_point);
}
