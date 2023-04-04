package com.cafeHi.www.member.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MemberAuthEntity {

    @Id @GeneratedValue
    @Column(name = "member_auth_code")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code")
    private MemberEntity memberEntity;
    private String memberAuth; // 스프링 시큐리티 사용자 권한 : ROLE_USER, ROLE_ADMIN ...
    private LocalDateTime memberAuthWritetime; // 사용자 정보 권한 등록일
    private LocalDateTime memberAuthUpdatetime; // 사용자 정보 권한 수정일
}
