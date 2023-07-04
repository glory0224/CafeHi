package com.cafeHi.www.member.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
//@Setter
public class MemberAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_auth_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code")
    private Member member;
    private String memberAuth; // 스프링 시큐리티 사용자 권한 : ROLE_USER, ROLE_ADMIN ...
    private LocalDateTime memberAuthWritetime; // 사용자 정보 권한 등록일
    private LocalDateTime memberAuthUpdatetime; // 사용자 정보 권한 수정일

    public void signupMemberAuth(Member member) {
        this.member = member;
        this.memberAuth = "ROLE_USER";
        this.memberAuthWritetime = LocalDateTime.now();
        this.memberAuthUpdatetime = LocalDateTime.now();
    }
}
