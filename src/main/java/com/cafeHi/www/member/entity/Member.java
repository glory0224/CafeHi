package com.cafeHi.www.member.entity;

import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_code")
    private Long id;
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

    @OneToMany(mappedBy = "member")
    private List<MemberAuth> memberAuthEntities = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private Membership membership;

    public void signupMember(String memberId, String memberPw, String memberName, String memberContact, String memberEmail
                        , String memberRoadAddress, String memberJibunAddress, String memberDetailAddress) {
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberContact = memberContact;
        this.memberEmail = memberEmail;

        if(memberRoadAddress.equals(null) || memberRoadAddress.equals("")){
            this.memberRoadAddress = "도로명주소없음";
        }else{
            this.memberRoadAddress = memberRoadAddress;
        }

        if(memberJibunAddress.equals(null) || memberJibunAddress.equals("")){
            this.memberJibunAddress = "지번주소없음";
        }else {
            this.memberJibunAddress = memberJibunAddress;
        }

        this.memberDetailAddress = memberDetailAddress;
        this.enabled = true;
        this.memberWritetime = LocalDateTime.now();
        this.memberUpdatetime = LocalDateTime.now();
    }

    public void modifyMember(Long id, String memberId, String memberName, String memberContact, String memberEmail
            , String memberRoadAddress, String memberJibunAddress, String memberDetailAddress) {
        this.id = id;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberContact = memberContact;
        this.memberEmail = memberEmail;

        if(memberRoadAddress.equals(null) || memberRoadAddress.equals("")){
            this.memberRoadAddress = "도로명주소없음";
        }else{
            this.memberRoadAddress = memberRoadAddress;
        }

        if(memberJibunAddress.equals(null) || memberJibunAddress.equals("")){
            this.memberJibunAddress = "지번주소없음";
        }else {
            this.memberJibunAddress = memberJibunAddress;
        }
        this.memberDetailAddress = memberDetailAddress;
        this.memberUpdatetime = LocalDateTime.now();
    }

    // password encode
    public void passwordEncode(String rowPw) {
        BCryptPasswordEncoder passwordEncode = new BCryptPasswordEncoder();
        this.memberPw =  passwordEncode.encode(rowPw);
    }

    // password decode



}
