package com.cafeHi.www.member.service;

import com.cafeHi.www.member.MemberAuthority;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final MemberRepository memberRepository;

    public List<Member> findMembersByAdmin() {
        return memberRepository.findMembersByAdmin(MemberAuthority.ROLE_USER, MemberAuthority.ROLE_MANAGER);
    }

}
