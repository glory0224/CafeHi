package com.cafeHi.www.member.dto;

import java.util.Collection;
import java.util.stream.Collectors;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class CustomUser extends User {
	
	private static final long serialVersionUID = 1L;
	
	private MemberDTO memberDTO;
	
	
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public CustomUser(MemberDTO mem) {
		
		super(mem.getMember_id(), mem.getMember_pw(), mem.getAuthList().stream().map(auth -> new SimpleGrantedAuthority(auth.getMember_auth())).collect(Collectors.toList()));
		
		
		this.memberDTO = mem;
		
		
		
	}

	
	
}
