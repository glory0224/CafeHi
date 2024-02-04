package com.cafeHi.www.member.repository;

import com.cafeHi.www.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    boolean existsByMemberId(String memberId);
    @Query("SELECT m.memberId FROM Member m where m.memberEmail = :memberEmail")
    String findMemberIdByMemberEmail(String memberEmail);
}
