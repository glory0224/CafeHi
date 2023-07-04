package com.cafeHi.www.member.service;

import com.cafeHi.www.member.dto.CustomMember;
import com.cafeHi.www.member.dto.MemberInfo;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.entity.Membership;
import com.cafeHi.www.member.repository.MemberRepository;
import com.cafeHi.www.member.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Component
public class CustomUserDetailService implements UserDetailsService{

	private final MemberRepository memberRepository;

	private final MembershipRepository membershipRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.warn("Load User By UserName : " + username);
		
		Member memberEntity = memberRepository.findByMemberId(username);

		Membership membershipEntity = membershipRepository.findMembershipbByMemberId(memberEntity);

		MemberInfo mem = new MemberInfo(memberEntity.getId(), memberEntity.getMemberId(), memberEntity.getMemberPw(), memberEntity.getMemberName(), memberEntity.getMemberContact()
										, memberEntity.getMemberEmail(), memberEntity.getMemberRoadAddress(), memberEntity.getMemberJibunAddress()
										, memberEntity.getMemberDetailAddress(), memberEntity.isEnabled(), membershipEntity, memberEntity.getMemberAuthEntities());

		return mem ==  null ? null : new CustomMember(mem);
		
	}
	
	
	
}
