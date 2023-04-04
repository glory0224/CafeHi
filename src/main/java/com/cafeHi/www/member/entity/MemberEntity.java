package com.cafeHi.www.member.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class MemberEntity {

    @Id @GeneratedValue
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

    @OneToMany(mappedBy = "memberEntity")
    private List<MemberAuthEntity> memberAuthEntities = new ArrayList<>();

}
