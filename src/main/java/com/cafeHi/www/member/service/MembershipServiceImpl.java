package com.cafeHi.www.member.service;

import org.springframework.stereotype.Service;

import com.cafeHi.www.mapper.member.MembershipMapper;
import com.cafeHi.www.member.MembershipGrade;
import com.cafeHi.www.member.dto.MembershipDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MembershipServiceImpl implements MembershipService{
	
	private final MembershipMapper membershipMapper;

	@Override
	public void insertMembership(MembershipDTO membershipDTO) {
		membershipMapper.insertMembership(membershipDTO);
	}
	
	@Override
	public MembershipDTO getMembership(Long member_code) {

		return membershipMapper.getMembership(member_code);
	}

	@Override
	public void updateMembershipPoint(MembershipDTO membershipDTO) {
		
		// 새로운 포인트(int 형변환으로 소수점 버림) + 기존 포인트
		double totalPoint = Math.floor(membershipDTO.getMembership_point() + membershipDTO.getMembership_new_point());
		

		// 회원 Grade 변경 로직

		changeGrade(membershipDTO, totalPoint);


		membershipMapper.updateMembershipPoint(membershipDTO);
		
	}



	@Override
	public void minusMembershipPoint(MembershipDTO getMembershipDTO, int total_order_price_point) {

		int returnPoint = getMembershipDTO.getMembership_point() - total_order_price_point;


		changeGrade(getMembershipDTO, returnPoint);

		membershipMapper.updateMembershipPoint(getMembershipDTO);

	}

	private static void changeGrade(MembershipDTO membershipDTO, double totalPoint) {
		if (MembershipGrade.STANDARD.getBasePoint() <= totalPoint && totalPoint < MembershipGrade.SILVER.getBasePoint()) {
			membershipDTO.updateMembershipInfo(MembershipGrade.STANDARD.getGrade(), totalPoint);
		}

		if (MembershipGrade.SILVER.getBasePoint() <= totalPoint && totalPoint < MembershipGrade.GOLD.getBasePoint()) {
			membershipDTO.updateMembershipInfo(MembershipGrade.SILVER.getGrade(), totalPoint);
		}

		if (MembershipGrade.GOLD.getBasePoint() <= totalPoint && totalPoint < MembershipGrade.VIP.getBasePoint()) {
			membershipDTO.updateMembershipInfo(MembershipGrade.GOLD.getGrade(), totalPoint);
		}

		if (MembershipGrade.VIP.getBasePoint() <= totalPoint) {
			membershipDTO.updateMembershipInfo(MembershipGrade.VIP.getGrade(), totalPoint);
		}
	}

}
