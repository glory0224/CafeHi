package com.cafeHi.www.member.repository;

import com.cafeHi.www.member.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipJpaRepository extends JpaRepository<Membership, Long> {
}
