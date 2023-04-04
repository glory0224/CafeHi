package com.cafeHi.www.mapper.member;

import com.cafeHi.www.member.dto.MemberAuthDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberAuthMapper {
	
	 Long insertMemberAuth(MemberAuthDTO memberAuthDTO);
}
