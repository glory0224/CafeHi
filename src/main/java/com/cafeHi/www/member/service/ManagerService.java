package com.cafeHi.www.member.service;

import com.cafeHi.www.member.MemberAuthority;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.repository.MemberRepository;
import com.cafeHi.www.menu.entity.Menu;
import com.cafeHi.www.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ManagerService {

    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;


    public List<Member> findMembersByManager() {
        String userAuth =  MemberAuthority.ROLE_USER.toString();
        return memberRepository.findMembersByManager(userAuth);
    }

    public List<Menu> findMenuByManager() {
        return menuRepository.findAll();
    }

}
