package com.cafeHi.www.mapper;

import java.time.LocalDateTime;

import com.cafeHi.www.member.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.cafeHi.www.mapper.member.MemberAuthMapper;
import com.cafeHi.www.mapper.member.MemberMapper;
import com.cafeHi.www.mapper.member.MembershipMapper;
import com.cafeHi.www.member.dto.MemberAuthDTO;
import com.cafeHi.www.member.dto.MembershipDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional
public class MemberEntityDTOMapperTest {
	
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


		MemberDTO memberDTO = new MemberDTO("user", "user", "user", "0101111-", "testMail@Com", "대한민국 도로명주소", "대한민국 지번주소", "대한민국 어딘가 ", true, LocalDateTime.now(), LocalDateTime.now());

		memberDTO.setMember_pw(passwordEncoder.encode(memberDTO.getMember_pw()));

		memberMapper.insertMember(memberDTO);

		Long member_code = memberDTO.getMember_code();

		MemberAuthDTO memberAuthDTO = new MemberAuthDTO();

		memberAuthDTO.setMemberAuthInfo(member_code);

		memberAuthMapper.insertMemberAuth(memberAuthDTO);

		MembershipDTO membershipDTO = new MembershipDTO();

		membershipDTO.createMembership(member_code);

		membershipMapper.insertMembership(membershipDTO);


	}
		
	}


