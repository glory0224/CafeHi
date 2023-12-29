package com.cafeHi.www.member.entity;

import com.cafeHi.www.resources.entity.Resources;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "memberAuthSet")
    @OrderBy("ordernum desc")
    private Set<Resources> resourcesSet = new LinkedHashSet<>();

    // 유저 권한
    public void signupUserAuth(Member member) {
        this.member = member;
        this.memberAuth = "ROLE_USER";
        this.memberAuthWritetime = LocalDateTime.now();
        this.memberAuthUpdatetime = LocalDateTime.now();
    }

    // 매니저 권한
    public void signupManagerAuth(Member member) {
        this.member = member;
        this.memberAuth = "ROLE_MANAGER";
        this.memberAuthWritetime = LocalDateTime.now();
        this.memberAuthUpdatetime = LocalDateTime.now();
    }

    // 관리자 권한
    public void signupAdminAuth(Member member) {
        this.member = member;
        this.memberAuth = "ROLE_ADMIN";
        this.memberAuthWritetime = LocalDateTime.now();
        this.memberAuthUpdatetime = LocalDateTime.now();
    }


}
