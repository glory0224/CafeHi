package com.cafeHi.www.member.repository;

import com.cafeHi.www.member.MemberAuthority;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.entity.MemberAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberRepository {

    private final EntityManager em;

    public Member saveMember(Member member) {

        em.persist(member);

        return member;
    }

    public void saveMemberAuth(MemberAuth memberAuth) {
        em.persist(memberAuth);
    }

    public Member findMember(Long id) {
        return em.find(Member.class, id);
    }

    public MemberAuth findMemberAuth(Long id) {
        return em.find(MemberAuth.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findById(String memberId) {
        return em.createQuery("select m from Member m where m.memberId = :memberId", Member.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    // 유저권한 멤버 조회
    public List<Member> findMembersByManager(String userAuth) {

       return em.createQuery("select m from Member m join m.memberAuthEntities a where a.memberAuth = :userAuth", Member.class)
                    .setParameter("userAuth", userAuth)
                    .getResultList();
    }

    // 유저권한, 매니저권한의 멤버 조회
    public List<Member> findMembersByAdmin(MemberAuthority userAuth, MemberAuthority managerAuth) {
        return em.createQuery("select m from Member m join m.memberAuthEntities a where a.memberAuth in (:userAuth,:managerAuth)", Member.class)
                .setParameter("userAuth", userAuth)
                .setParameter("managerAuth", managerAuth)
                .getResultList();
    }

    // 아이디로 멤버 조회
    public Optional<Member> findByMemberId(String memberId) {
        try {
            Member member = em.createQuery("select m from Member m where m.memberId = :memberId", Member.class)
                    .setParameter("memberId", memberId)
                    .getSingleResult();
            return Optional.ofNullable(member);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }
    public void deleteMember(Long memberCode) {
        Member findMember = em.find(Member.class, memberCode);

        if (findMember != null) {
            // MemberAuth 엔티티 삭제
            List<MemberAuth> memberAuthEntities = findMember.getMemberAuthEntities();
            for (MemberAuth memberAuth : memberAuthEntities) {
                em.remove(memberAuth);
            }

            // Membership 엔티티 삭제
            em.remove(findMember.getMembership());

            // Member 엔티티 삭제
            em.remove(findMember);

        }



    }

    public String findMemberByEmail(String memberEmail) {
        TypedQuery<String> query =  em.createQuery("select m.memberId from Member m where m.memberEmail = :memberEmail", String.class)
                .setParameter("memberEmail", memberEmail);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // 원하는 조건에 맞는 결과가 없을 경우 null을 반환하거나 다른 처리를 수행할 수 있습니다.
        }

    }


}
