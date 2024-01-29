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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomUserDetailService implements UserDetailsService{

	private final MemberRepository memberRepository;
	private final MembershipRepository membershipRepository;

	/**
	 * 오버라이드한 loadUserByUsername 은 id만 체크, pw는 AuthenticationProvider 구현한 FormAuthenticationProvider 에서 체크
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MemberInfo mem = new MemberInfo();
		Optional<Member> memberEntity = memberRepository.findByMemberId(username);
		
		// member가 null이 아닌 경우
		if(memberEntity.isPresent()) {
			// 권한 조회
			Optional<MemberAuth> authorities = memberEntity.get().getMemberAuthEntities().stream()
					.filter(vo -> vo.getMemberAuth().equals("ROLE_USER")
							|| vo.getMemberAuth().equals("ROLE_MANAGER")
							|| vo.getMemberAuth().equals("ROLE_ADMIN"))
					.findAny();
			
			if(authorities.isPresent()) {
				String memberAuthName = authorities.get().getMemberAuth();

				// 권한에 따라 분기
				if("ROLE_USER".equals(memberAuthName)) {
					Membership membershipEntity = memberEntity.get().getMembership();
					if(membershipEntity == null) {
						throw new UsernameNotFoundException("Not Found Membership By userName");
					}

					mem.initUserAuthMember(memberEntity.get().getId(), memberEntity.get().getMemberId(), memberEntity.get().getMemberPw(), memberEntity.get().getMemberName(), memberEntity.get().getMemberContact()
							, memberEntity.get().getMemberEmail(), memberEntity.get().getMemberZipCode(), memberEntity.get().getMemberAddress()
							, memberEntity.get().getMemberDetailAddress(), memberEntity.get().isEnabled(), membershipEntity, memberEntity.get().getMemberAuthEntities());
				} else if("ROLE_MANAGER".equals(memberAuthName)) {
					mem.initManagerAuthMember(memberEntity.get().getId(), memberEntity.get().getMemberId(), memberEntity.get().getMemberPw(), memberEntity.get().getMemberName(), memberEntity.get().getMemberContact()
							, memberEntity.get().getMemberEmail(), memberEntity.get().getMemberZipCode(), memberEntity.get().getMemberAddress()
							, memberEntity.get().getMemberDetailAddress(), memberEntity.get().isEnabled(), memberEntity.get().getMemberAuthEntities());
				} else if("ROLE_ADMIN".equals(memberAuthName)) {
					mem.initAdminAuthMember(memberEntity.get().getId(), memberEntity.get().getMemberId(), memberEntity.get().getMemberPw(), memberEntity.get().getMemberName(), memberEntity.get().getMemberContact()
							, memberEntity.get().getMemberEmail(), memberEntity.get().getMemberZipCode(), memberEntity.get().getMemberAddress()
							, memberEntity.get().getMemberDetailAddress(), memberEntity.get().isEnabled(), memberEntity.get().getMemberAuthEntities());
				}

			}
			// 멤버가 없는 경우
		} else if(memberEntity.isEmpty()) {
			throw new UsernameNotFoundException("UsernameNotFoundException");
		}

		return mem ==  null ? null : new CustomUser(mem);
		
	}
	
	
	
}
