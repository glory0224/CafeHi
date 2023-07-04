package com.cafeHi.www.member.entity;

import com.cafeHi.member.MembershipGrade;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Slf4j
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code")
    private Member member; // 멤버 정보
    @Enumerated(EnumType.STRING)
    private MembershipGrade membershipGrade;
    private int membershipPoint;
    private LocalDateTime membershipWritetime;
    private LocalDateTime membershipUpdatetime;

    // 회원 가입 시 포인트 등록
    public void signupMembership(Member member) {
        this.member = member;
        this.membershipGrade = MembershipGrade.STANDARD;
        this.membershipPoint = 0;
        this.membershipWritetime = LocalDateTime.now();
        this.membershipUpdatetime = LocalDateTime.now();
    }

    // 주문시 포인트 추가
    public void updateMembership(double membershipNewPoint) {
        this.membershipPoint += membershipNewPoint;
    }

    // 주문 취소시 포인트 차감
    public void minusMembershipPoint(int totalOrderPricePoint) {
       this.membershipPoint -= totalOrderPricePoint;
    }

    // 등급 계산
    public void updateMemberGrade(int membershipPoint){
        if (MembershipGrade.STANDARD.getBasePoint() <= membershipPoint && membershipPoint < MembershipGrade.SILVER.getBasePoint()) {
            this.membershipGrade = MembershipGrade.STANDARD;
            this.membershipPoint = membershipPoint;
        }

        else if (MembershipGrade.SILVER.getBasePoint() <= membershipPoint && membershipPoint < MembershipGrade.GOLD.getBasePoint()) {
            this.membershipGrade = MembershipGrade.SILVER;
            this.membershipPoint = membershipPoint;
        }

        else if (MembershipGrade.GOLD.getBasePoint() <= membershipPoint && membershipPoint < MembershipGrade.VIP.getBasePoint()) {
            this.membershipGrade = MembershipGrade.GOLD;
            this.membershipPoint = membershipPoint;
        }
        if (MembershipGrade.VIP.getBasePoint() <= membershipPoint) {
            this.membershipGrade = MembershipGrade.VIP;
            this.membershipPoint = membershipPoint;
        }
    }


}
