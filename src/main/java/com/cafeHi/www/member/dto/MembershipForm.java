package com.cafeHi.www.member.dto;

import com.cafeHi.www.member.entity.Membership;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MembershipForm {

    private Long membershipCode;
    private Long memberCode;
    private String membershipGrade;
    private int membershipPoint;
    private double membershipNewPoint;
    private LocalDateTime membershipWritetime;
    private LocalDateTime membershipUpdatetime;

    public MembershipForm() {
    }

    public MembershipForm(Membership membership) {
        this.membershipCode = membership.getId();
        this.memberCode = membership.getMember().getId();
        this.membershipGrade = membership.getMembershipGrade().name();
        this.membershipPoint = membership.getMembershipPoint();
        this.membershipWritetime = membership.getMembershipWritetime();
        this.membershipUpdatetime = membership.getMembershipUpdatetime();
    }

}
