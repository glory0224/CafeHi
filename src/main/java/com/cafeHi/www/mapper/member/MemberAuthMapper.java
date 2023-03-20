package com.cafeHi.www.mapper.member;

import com.cafeHi.www.member.dto.MemberAuth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberAuthMapper {
	
	public int insertMemberAuth(MemberAuth memberAuth);
}
