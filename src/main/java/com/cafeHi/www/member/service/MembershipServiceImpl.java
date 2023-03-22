package com.cafeHi.www.member.service;

import java.time.LocalDateTime;

import com.cafeHi.www.order.dto.OrderMenu;
import org.springframework.stereotype.Service;

import com.cafeHi.www.mapper.member.MembershipMapper;
import com.cafeHi.www.member.MembershipGrade;
import com.cafeHi.www.member.dto.Membership;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MembershipServiceImpl implements MembershipService{
	
	private final MembershipMapper membershipMapper;

	@Override
	public void insertMembership(Membership membership) {
		membershipMapper.insertMembership(membership);
	}
	
	@Override
	public Membership getMembership(int member_code) {
		return membershipMapper.getMembership(member_code);
	}

	@Override
	public void updateMembershipPoint(Membership membership) {
		
		// 새로운 포인트(int 형변환으로 소수점 버림) + 기존 포인트
		double totalPoint = Math.floor(membership.getMembership_point() + membership.getMembership_new_point());
		
		log.info("totalPoint = {}", totalPoint);
		
		// 회원 Grade 변경 로직

		changeGrade(membership, totalPoint);

		log.info("total membership point = {}", membership.getMembership_point());
		
		int result =  membershipMapper.updateMembershipPoint(membership);
		
		log.info("updateResult = {}", result);
	}



	@Override
	public void minusMembershipPoint(Membership getMembership, int total_order_price_point) {

		int returnPoint = getMembership.getMembership_point() - total_order_price_point;

		log.info("returnPoint = {}", returnPoint);

		changeGrade(getMembership, returnPoint);

		int result =  membershipMapper.updateMembershipPoint(getMembership);

		log.info("updateResult = {}", result);

	}

	private static void changeGrade(Membership membership, double totalPoint) {
		if (MembershipGrade.STANDARD.getBasePoint() <= totalPoint && totalPoint < MembershipGrade.SILVER.getBasePoint()) {
			membership.updateMembershipInfo(MembershipGrade.STANDARD.getGrade(), totalPoint);
		}

		if (MembershipGrade.SILVER.getBasePoint() <= totalPoint && totalPoint < MembershipGrade.GOLD.getBasePoint()) {
			membership.updateMembershipInfo(MembershipGrade.SILVER.getGrade(), totalPoint);
		}

		if (MembershipGrade.GOLD.getBasePoint() <= totalPoint && totalPoint < MembershipGrade.VIP.getBasePoint()) {
			membership.updateMembershipInfo(MembershipGrade.GOLD.getGrade(), totalPoint);
		}

		if (MembershipGrade.VIP.getBasePoint() <= totalPoint) {
			membership.updateMembershipInfo(MembershipGrade.VIP.getGrade(), totalPoint);
		}
	}

}
