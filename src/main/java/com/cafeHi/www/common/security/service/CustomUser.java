package com.cafeHi.www.common.security.service;

import com.cafeHi.www.member.dto.MemberInfo;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.stream.Collectors;

@Getter
public class CustomUser extends User {

    private MemberInfo memberInfo;

    public CustomUser(MemberInfo memberInfo) {
        super(memberInfo.getMemberId(), memberInfo.getMemberPw(), memberInfo.getMemberAuthEntities().stream().map(memberAuth -> new SimpleGrantedAuthority(memberAuth.getMemberAuth())).collect(Collectors.toList()));
        this.memberInfo = memberInfo;
    }

}
