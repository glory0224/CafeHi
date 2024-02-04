package com.cafeHi.www.member.repository;

import com.cafeHi.www.member.entity.MemberAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAuthJpaRepository extends JpaRepository<MemberAuth, Long> {

}
