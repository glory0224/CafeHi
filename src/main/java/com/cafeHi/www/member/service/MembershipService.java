package com.cafeHi.www.member.service;

import com.cafeHi.www.common.exception.EntityNotFoundException;
import com.cafeHi.www.member.entity.Membership;
import com.cafeHi.www.member.repository.MembershipJpaRepository;
import com.cafeHi.www.member.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MembershipService {

//    private final MembershipRepository membershipRepository;
    private final MembershipJpaRepository membershipJpaRepository;

    @Transactional
    public Long joinMembership(Membership membership) {
//        membershipRepository.saveMembership(membership);
        Membership findMembership = membershipJpaRepository.save(membership);
        return findMembership.getId();
    }

    public Membership findMembership(Long MembershipId) {
//        return membershipRepository.findMembership(MembershipId);
        Optional<Membership> findMembership = membershipJpaRepository.findById(MembershipId);
        return findMembership.orElseThrow(() -> new EntityNotFoundException("해당하는 멤버쉽이 없습니다."));
    }

}
