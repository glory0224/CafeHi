package com.cafeHi.www.member.service;

import com.cafeHi.www.member.dto.Membership;
import com.cafeHi.www.order.dto.OrderMenu;

public interface MembershipService {
	
	void insertMembership(Membership membership);
	Membership getMembership(Long member_code);
	
	void updateMembershipPoint(Membership membership);

    void minusMembershipPoint(Membership getMembership, int total_order_price_point);
}
