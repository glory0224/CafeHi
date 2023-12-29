package com.cafeHi.www.common.security.metadatasource;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap = new LinkedHashMap<>();

    public UrlFilterInvocationSecurityMetadataSource(LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourcesMap) {
        this.requestMap = resourcesMap;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        // 사용자가 요청한 url 요청 객체 데이터 추출
        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        // url에 접근 권한 Map 방식으로 추가
        // SecurityConfig가 아닌 직접 커스텀해서 구현한 방식
        requestMap.put(new AntPathRequestMatcher("/member/**"), Arrays.asList(new SecurityConfig("ROLE_USER")));
        requestMap.put(new AntPathRequestMatcher("/qna/member/**"), Arrays.asList(new SecurityConfig("ROLE_USER")));
        requestMap.put(new AntPathRequestMatcher("/manager/**"), Arrays.asList(new SecurityConfig("ROLE_MANAGER")));
        requestMap.put(new AntPathRequestMatcher("/qna/manager/**"), Arrays.asList(new SecurityConfig("ROLE_MANAGER")));
        requestMap.put(new AntPathRequestMatcher("/admin/**"), Arrays.asList(new SecurityConfig("ROLE_ADMIN")));
        requestMap.put(new AntPathRequestMatcher("/qna/admin/**"), Arrays.asList(new SecurityConfig("ROLE_ADMIN")));

        if(requestMap != null) {
            for(Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()) {
                RequestMatcher matcher = entry.getKey();
                // db에 저장한 url 자원 정보, 권한정보 확인
                if (matcher.matches(request)) {
                    return entry.getValue();    // 일치하는 경우 권한 정보 반환
                }
            }
        }



        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();
        this.requestMap.values().forEach(allAttributes::addAll);
        return allAttributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
