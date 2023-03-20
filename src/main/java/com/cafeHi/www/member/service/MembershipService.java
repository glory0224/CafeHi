package com.cafeHi.www.member.service;

import com.cafeHi.www.member.dto.Membership;

public interface MembershipService {
	
	public void insertMembership(Membership membership);
	public Membership getMembership(int member_code);
	
	public void updateMembershipPoint(Membership membership);
	
}
