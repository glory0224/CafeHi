package com.cafeHi.www.member.service;

import com.cafeHi.www.member.dto.Member;
import com.cafeHi.www.member.dto.MemberAuth;

public interface MemberService {

    int insertMember(Member member);

    int insertMemberAuth(MemberAuth memberAuth);

    void updateMemberName(Member member);
    void updateMemberContact(Member member);
    void updateMemberEmail(Member member);
    void updateMemberAddress(Member member);
    void updateMemberDetailAddress(Member member);

    int deleteMember(int member_code);

    Member getMember(int member_code);

    Member findMemberById(String member_id);

    int idCheck(String member_id);

    int checkEmail(String member_email);

}
