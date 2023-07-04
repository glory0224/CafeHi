package com.cafeHi.www.member.dto;

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
    private String memberRoadAddress; // 사용자 도로명 주소
    private String memberJibunAddress; // 사용자 지번 주소
    private String memberDetailAddress; // 사용자 상세 주소
    private boolean enabled; // 스프링 시큐리티 권한 사용 여부
    private LocalDateTime memberWritetime; // 사용자 등록일
    private LocalDateTime memberUpdatetime; // 사용자 수정일

    private Membership membership; // 멤버쉽 정보

    @OneToMany(mappedBy = "member")
    private List<MemberAuth> memberAuthEntities = new ArrayList<>();

    public MemberInfo(Long memberCode, String memberId, String memberPw, String memberName, String memberContact, String memberEmail, String memberRoadAddress, String memberJibunAddress, String memberDetailAddress,  boolean enabled, Membership membership, List<MemberAuth> memberAuthEntities) {
        this.memberCode = memberCode;
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberContact = memberContact;
        this.memberEmail = memberEmail;
        this.memberRoadAddress = memberRoadAddress;
        this.memberJibunAddress = memberJibunAddress;
        this.memberDetailAddress = memberDetailAddress;
        this.enabled = enabled;
        this.membership = membership;
        this.memberAuthEntities = memberAuthEntities;
    }
}
