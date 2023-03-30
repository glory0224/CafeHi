package com.cafeHi.www.member.service;

import com.cafeHi.www.mapper.member.MemberMapper;
import com.cafeHi.www.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberMapper memberMapper;

    @Override
    @Transactional
    public int insertMember(Member member) {
        return memberMapper.insertMember(member);
    }

    @Override
    @Transactional
    public void updateMemberName(Member member) {
        memberMapper.updateMemberName(member);
    }

    @Override
    @Transactional
    public void updateMemberContact(Member member) {
        memberMapper.updateMemberContact(member);
    }

    @Override
    @Transactional
    public void updateMemberEmail(Member member) {
        memberMapper.updateMemberEmail(member);
    }

    @Override
    @Transactional
    public void updateMemberAddress(Member member) {
        memberMapper.updateMemberAddress(member);
    }

    @Override
    @Transactional
    public void updateMemberDetailAddress(Member member) {
        memberMapper.updateMemberDetailAddress(member);
    }

    @Override
    @Transactional
    public int deleteMember(int member_code) {

        return memberMapper.deleteMember(member_code);
    }


    @Override
    public Member getMember(int member_code) {
        return memberMapper.getMember(member_code);
    }

    @Override
    public Member findMemberById(String member_id) {
        return memberMapper.findMemberById(member_id);
    }

    @Override
    public int idCheck(String member_id) {
        return memberMapper.idCheck(member_id);
    }

    @Override
    public int checkEmail(String member_email) {
        return memberMapper.checkEmail(member_email);
    }
}
