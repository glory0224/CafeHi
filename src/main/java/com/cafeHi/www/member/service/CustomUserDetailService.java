package com.cafeHi.www.member.service;

import com.cafeHi.www.member.dto.MemberDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cafeHi.www.mapper.member.MemberMapper;
import com.cafeHi.www.member.dto.CustomUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Component
public class CustomUserDetailService implements UserDetailsService{

	private final MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.warn("Load User By UserName : " + username);
		
		MemberDTO mem = memberMapper.findMemberById(username); // 시큐리티 검증

		return mem ==  null ? null : new CustomUser(mem);
		
	}
	
	
	
}
