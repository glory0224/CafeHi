package com.cafeHi.www.resources.service;

import com.cafeHi.www.resources.entity.Resources;
import com.cafeHi.www.resources.repository.ResourcesRepository;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SecurityResourceService {

    private ResourcesRepository resourcesRepository;

    public SecurityResourceService(ResourcesRepository resourcesRepository) {
        this.resourcesRepository = resourcesRepository;
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {

        LinkedHashMap<RequestMatcher , List<ConfigAttribute>> result = new LinkedHashMap<>();

        List<Resources> resourceList = resourcesRepository.findAllResources();

        resourceList.forEach(resources -> {
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            resources.getMemberAuthSet().forEach(memberAuth -> {
                configAttributeList.add(new SecurityConfig(memberAuth.getMemberAuth()));   // db에서 가져오는 Auth를 추가
                result.put(new AntPathRequestMatcher(resources.getResourceName()), configAttributeList);    // resources, auth 타입으로 key, value로 매핑
            });

        });

        return result;
    }
}
