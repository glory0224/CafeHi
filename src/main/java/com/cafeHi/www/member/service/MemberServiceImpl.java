package com.cafeHi.www.member.service;

import com.cafeHi.www.mapper.member.MemberAuthMapper;
import com.cafeHi.www.mapper.member.MemberMapper;
import com.cafeHi.www.member.dto.MemberDTO;
import com.cafeHi.www.member.dto.MemberAuthDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberMapper memberMapper;

    private final MemberAuthMapper memberAuthMapper;

    @Override
    @Transactional
    public Long insertMember(MemberDTO memberDTO) {
        return memberMapper.insertMember(memberDTO);
    }

    @Override
    @Transactional
    public Long insertMemberAuth(MemberAuthDTO memberAuthDTO) {

        return memberAuthMapper.insertMemberAuth(memberAuthDTO);
    }

    @Override
    @Transactional
    public void updateMemberName(MemberDTO memberDTO) {
        memberMapper.updateMemberName(memberDTO);
    }

    @Override
    @Transactional
    public void updateMemberContact(MemberDTO memberDTO) {
        memberMapper.updateMemberContact(memberDTO);
    }

    @Override
    @Transactional
    public void updateMemberEmail(MemberDTO memberDTO) {
        memberMapper.updateMemberEmail(memberDTO);
    }

    @Override
    @Transactional
    public void updateMemberAddress(MemberDTO memberDTO) {
        memberMapper.updateMemberAddress(memberDTO);
    }

    @Override
    @Transactional
    public void updateMemberDetailAddress(MemberDTO memberDTO) {
        memberMapper.updateMemberDetailAddress(memberDTO);
    }

    @Override
    @Transactional
    public int deleteMember(Long member_code) {

        return memberMapper.deleteMember(member_code);
    }


    @Override
    public MemberDTO getMember(Long member_code) {
        return memberMapper.getMember(member_code);
    }

    @Override
    public MemberDTO findMemberById(String member_id) {
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

    @Override
    public boolean isPasswordMatched(Long member_code, String password) {
         String hashedPassword = memberMapper.getPasswordByMemberCode(member_code);
         return new BCryptPasswordEncoder().matches(password, hashedPassword);
    }
}
