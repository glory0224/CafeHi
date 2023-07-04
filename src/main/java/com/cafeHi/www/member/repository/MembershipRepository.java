package com.cafeHi.www.member.repository;

import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.entity.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MembershipRepository {

    private final EntityManager em;

    public void saveMembership(Membership membership) {
        em.persist(membership);
    }

    public Membership findMembership(Long id) {
        return em.find(Membership.class, id);
    }



    public Membership findMembershipbByMemberId(Member member) {
        return em.createQuery("select ms from Membership ms where ms.member = :member", Membership.class)
                .setParameter("member", member)
                .getSingleResult();
    }
}
