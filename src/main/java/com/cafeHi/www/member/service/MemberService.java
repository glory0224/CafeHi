package com.cafeHi.www.member.service;

import com.cafeHi.www.member.dto.MemberDTO;
import com.cafeHi.www.member.dto.MemberAuthDTO;

public interface MemberService {

    Long insertMember(MemberDTO memberDTO);

    Long insertMemberAuth(MemberAuthDTO memberAuthDTO);

    void updateMemberName(MemberDTO memberDTO);
    void updateMemberContact(MemberDTO memberDTO);
    void updateMemberEmail(MemberDTO memberDTO);
    void updateMemberAddress(MemberDTO memberDTO);
    void updateMemberDetailAddress(MemberDTO memberDTO);

    int deleteMember(Long member_code);

    MemberDTO getMember(Long member_code);

    MemberDTO findMemberById(String member_id);

    int idCheck(String member_id);

    int checkEmail(String member_email);

}
