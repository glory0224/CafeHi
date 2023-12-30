package com.cafeHi.www.common.security.init;

import com.cafeHi.www.member.service.MemberAuthHierachyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Component;

@Component
public class SecurityInitializer implements ApplicationRunner {

    @Autowired
    private MemberAuthHierachyService memberAuthHierachyService;

    @Autowired
    private RoleHierarchyImpl roleHierarchy;

    // Application 동작 시점에 계층 권한 구조 만들기
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // formatting된 String 문자열을 roleHierarchy 에 저장
        String allHierarchy = memberAuthHierachyService.findAllHierarchy();
        roleHierarchy.setHierarchy(allHierarchy);
    }
}
