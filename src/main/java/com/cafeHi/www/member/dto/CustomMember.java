package com.cafeHi.www.member.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class CustomMember extends User {

    private MemberInfo memberInfo;

    public CustomMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomMember(MemberInfo memberInfo) {
        super(memberInfo.getMemberId(), memberInfo.getMemberPw(), memberInfo.getMemberAuthEntities().stream().map(memberAuth -> new SimpleGrantedAuthority(memberAuth.getMemberAuth())).collect(Collectors.toList()));
        this.memberInfo = memberInfo;
    }

}
