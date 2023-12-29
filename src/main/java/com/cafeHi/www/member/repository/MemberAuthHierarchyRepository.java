package com.cafeHi.www.member.repository;

import com.cafeHi.www.member.entity.MemberAuthHierachy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAuthHierarchyRepository extends JpaRepository<MemberAuthHierachy, Long> {

    MemberAuthHierachy findByChildName(String AuthName);
}
