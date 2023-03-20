package com.cafeHi.www.mapper;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.cafeHi.www.mapper.member.MemberAuthMapper;
import com.cafeHi.www.mapper.member.MemberMapper;
import com.cafeHi.www.mapper.member.MembershipMapper;
import com.cafeHi.www.member.dto.Member;
import com.cafeHi.www.member.dto.MemberAuth;
import com.cafeHi.www.member.dto.Membership;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional
public class MemberMapperTest {
	
	@Autowired
	MemberMapper memberMapper;
	
	@Autowired
	MemberAuthMapper memberAuthMapper;
	
	@Autowired
	MembershipMapper membershipMapper;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	

	@Test
	public void insertMemberTest() {


		Member member = new Member("user", "user", "user", "0101111-", "testMail@Com", "대한민국 도로명주소", "대한민국 지번주소", "대한민국 어딘가 ", true, LocalDateTime.now(), LocalDateTime.now());

		member.setMember_pw(passwordEncoder.encode(member.getMember_pw()));

		memberMapper.insertMember(member);

		int member_code = member.getMember_code();

		log.info("member_code = {}", member_code);

		MemberAuth memberAuth = new MemberAuth();

		memberAuth.setMemberAuthInfo(member_code);

		memberAuthMapper.insertMemberAuth(memberAuth);

		Membership membership = new Membership();

		membership.createMembership(member_code);

		membershipMapper.insertMembership(membership);


	}
		
	}


