package com.cafeHi.www.common.security.service;

import com.cafeHi.www.member.dto.MemberInfo;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.entity.MemberAuth;
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

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomUserDetailService implements UserDetailsService{

	private final MemberRepository memberRepository;

	private final MembershipRepository membershipRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
//		log.warn("Load User By UserName : " + username);
		MemberInfo mem = new MemberInfo();
		Member memberEntity = memberRepository.findByMemberId(username);

		if(memberEntity == null) {
			throw new UsernameNotFoundException("Not Found Member By userName");
		}

		// 권한 조회
		Optional<MemberAuth> authorities = memberEntity.getMemberAuthEntities().stream()
				.filter(vo -> vo.getMemberAuth().equals("ROLE_USER")
						|| vo.getMemberAuth().equals("ROLE_MANAGER")
						|| vo.getMemberAuth().equals("ROLE_ADMIN"))
				.findAny();

		if(authorities.isPresent()) {
			String memberAuthName = authorities.get().getMemberAuth();

			// 권한에 따라 분기
			if("ROLE_USER".equals(memberAuthName)) {
				Membership membershipEntity = membershipRepository.findMembershipbByMemberId(memberEntity);

				if(membershipEntity == null) {
					throw new UsernameNotFoundException("Not Found Membership By userName");
				}

				mem.initUserAuthMember(memberEntity.getId(), memberEntity.getMemberId(), memberEntity.getMemberPw(), memberEntity.getMemberName(), memberEntity.getMemberContact()
						, memberEntity.getMemberEmail(), memberEntity.getMemberRoadAddress(), memberEntity.getMemberJibunAddress()
						, memberEntity.getMemberDetailAddress(), memberEntity.isEnabled(), membershipEntity, memberEntity.getMemberAuthEntities());
			} else if("ROLE_MANAGER".equals(memberAuthName)) {
				mem.initManagerAuthMember(memberEntity.getId(), memberEntity.getMemberId(), memberEntity.getMemberPw(), memberEntity.getMemberName(), memberEntity.getMemberContact()
						, memberEntity.getMemberEmail(), memberEntity.getMemberRoadAddress(), memberEntity.getMemberJibunAddress()
						, memberEntity.getMemberDetailAddress(), memberEntity.isEnabled(), memberEntity.getMemberAuthEntities());
			} else if("ROLE_ADMIN".equals(memberAuthName)) {
				mem.initAdminAuthMember(memberEntity.getId(), memberEntity.getMemberId(), memberEntity.getMemberPw(), memberEntity.getMemberName(), memberEntity.getMemberContact()
						, memberEntity.getMemberEmail(), memberEntity.getMemberRoadAddress(), memberEntity.getMemberJibunAddress()
						, memberEntity.getMemberDetailAddress(), memberEntity.isEnabled(), memberEntity.getMemberAuthEntities());
			}

		}

		return mem ==  null ? null : new CustomUser(mem);
		
	}
	
	
	
}
