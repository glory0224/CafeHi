package com.cafeHi.www.member.service;

import com.cafeHi.www.member.entity.Membership;
import com.cafeHi.www.member.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;

    @Transactional
    public Long joinMembership(Membership membership) {
        membershipRepository.saveMembership(membership);

        return membership.getId();
    }

    public Membership findMembership(Long MembershipId) {
        return membershipRepository.findMembership(MembershipId);
    }

}
