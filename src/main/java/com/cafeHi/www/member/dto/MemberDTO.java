package com.cafeHi.www.member.dto;

import com.cafeHi.www.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MemberDTO {

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

    public MemberDTO() {
    }

    public MemberDTO(Member member) {
        this.memberCode = member.getId();
        this.memberId = member.getMemberId();
        this.memberPw = member.getMemberPw();
        this.memberName = member.getMemberName();
        this.memberContact = member.getMemberContact();
        this.memberEmail = member.getMemberEmail();
        this.memberZipCode = member.getMemberZipCode();
        this.memberAddress = member.getMemberAddress();
        this.enabled = member.isEnabled();
        this.memberWritetime = member.getMemberWritetime();
        this.memberUpdatetime = member.getMemberUpdatetime();
    }


}
