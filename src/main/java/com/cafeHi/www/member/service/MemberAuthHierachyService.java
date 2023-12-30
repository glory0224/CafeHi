package com.cafeHi.www.member.service;

import com.cafeHi.www.member.entity.MemberAuthHierachy;
import com.cafeHi.www.member.repository.MemberAuthHierarchyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
public class MemberAuthHierachyService {

    @Autowired
    private MemberAuthHierarchyRepository memberAuthHierarchyRepository;

    // 권한별 계층 String 문자열 만드는 로직
    @Transactional
    public String findAllHierarchy() {

        List<MemberAuthHierachy> memberAuthHierachy = memberAuthHierarchyRepository.findAll();

        Iterator<MemberAuthHierachy> iterator = memberAuthHierachy.iterator();

        StringBuilder concatAuthority = new StringBuilder();

        while(iterator.hasNext()) {
            MemberAuthHierachy authHierachy = iterator.next();
            if(authHierachy.getParentName() != null) {
                concatAuthority.append(authHierachy.getParentName().getChildName());
                concatAuthority.append(" > ");
                concatAuthority.append(authHierachy.getChildName());
                concatAuthority.append("\n");
            }
        }
        return concatAuthority.toString();
    }

}
