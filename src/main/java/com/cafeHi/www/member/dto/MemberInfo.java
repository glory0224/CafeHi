package com.cafeHi.www.member.dto;

import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.entity.MemberAuth;
import com.cafeHi.www.member.entity.Membership;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class MemberInfo {


    private Long memberCode;
    private String memberId; // 사용자 아이디
    private String memberPw; // 사용자 비밀번호
    private String memberName; // 사용자 이름
    private String memberContact; // 사용자 연락처
    private String memberEmail; // 사용자 계정 인증용 이메일
    private String memberZipCode; // 우편번호
    private String memberAddress; // 사용자 주소
    private String memberDetailAddress; // 사용자 상세 주소
    private boolean enabled; // 스프링 시큐리티 권한 사용 여부
    private LocalDateTime memberWritetime; // 사용자 등록일
    private LocalDateTime memberUpdatetime; // 사용자 수정일

    private Membership membership; // 멤버쉽 정보

    @OneToMany(mappedBy = "member")
    private List<MemberAuth> memberAuthEntities = new ArrayList<>();

    public MemberInfo(Long memberCode, String memberId, String memberPw, String memberName, String memberContact, String memberEmail, String memberZipCode, String memberAddress, String memberDetailAddress,  boolean enabled, Membership membership, List<MemberAuth> memberAuthEntities) {
        this.memberCode = memberCode;
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberContact = memberContact;
        this.memberEmail = memberEmail;
        this.memberZipCode = memberZipCode;
        this.memberAddress = memberAddress;
        this.memberDetailAddress = memberDetailAddress;
        this.enabled = enabled;
        this.membership = membership;
        this.memberAuthEntities = memberAuthEntities;
    }

    // 각 권한별 사용하는 기능이 달라서 초기화 분리

    // 유저 권한 멤버
    public void initUserAuthMember(Long memberCode, String memberId, String memberPw, String memberName, String memberContact, String memberEmail, String memberZipCode, String memberAddress, String memberDetailAddress,  boolean enabled, Membership membership, List<MemberAuth> memberAuthEntities) {
        this.memberCode = memberCode;
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberContact = memberContact;
        this.memberEmail = memberEmail;
        this.memberZipCode = memberZipCode;
        this.memberAddress = memberAddress;
        this.memberDetailAddress = memberDetailAddress;
        this.enabled = enabled;
        this.membership = membership;
        this.memberAuthEntities = memberAuthEntities;
    }

    // 매니저 권한 멤버
    public void initManagerAuthMember(Long memberCode, String memberId, String memberPw, String memberName, String memberContact, String memberEmail, String memberZipCode, String memberAddress, String memberDetailAddress,  boolean enabled, List<MemberAuth> memberAuthEntities) {
        this.memberCode = memberCode;
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberContact = memberContact;
        this.memberEmail = memberEmail;
        this.memberZipCode = memberZipCode;
        this.memberAddress = memberAddress;
        this.memberDetailAddress = memberDetailAddress;
        this.enabled = enabled;
        this.memberAuthEntities = memberAuthEntities;
    }

    // 관리자 권한 멤버
    public void initAdminAuthMember(Long memberCode, String memberId, String memberPw, String memberName, String memberContact, String memberEmail, String memberZipCode, String memberAddress, String memberDetailAddress,  boolean enabled, List<MemberAuth> memberAuthEntities) {
        this.memberCode = memberCode;
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberContact = memberContact;
        this.memberEmail = memberEmail;
        this.memberZipCode = memberZipCode;
        this.memberAddress = memberAddress;
        this.memberDetailAddress = memberDetailAddress;
        this.enabled = enabled;
        this.memberAuthEntities = memberAuthEntities;
    }

    public Member convertToMember() {
        Member member = new Member();
        member.initMember(this);
        return member;
    }
}
