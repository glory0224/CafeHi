package com.cafeHi.www.member.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MembershipEntity {
    @Id @GeneratedValue
    @Column(name = "membership_code")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code")
    private MemberEntity memberEntity; // 멤버 정보
    private String membershipGrade;
    private int membershipPoint;
    private double membershipNewPoint;
    private LocalDateTime membershipWritetime;
    private LocalDateTime membershipUpdatetime;
}
