package com.cafeHi.www.common.security.metadatasource;

import com.cafeHi.www.resources.service.SecurityResourceService;
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

    private SecurityResourceService securityResourceService;

    public UrlFilterInvocationSecurityMetadataSource(LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourcesMap, SecurityResourceService securityResourceService) {
        this.requestMap = resourcesMap;
        this.securityResourceService = securityResourceService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        // 사용자가 요청한 url 요청 객체 데이터 추출
        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        // url에 접근 권한 Map 방식으로 추가
        // SecurityConfig가 아닌 직접 커스텀해서 구현한 방식
//        requestMap.put(new AntPathRequestMatcher("/member/**"), Arrays.asList(new SecurityConfig("ROLE_USER")));
//        requestMap.put(new AntPathRequestMatcher("/qna/member/**"), Arrays.asList(new SecurityConfig("ROLE_USER")));
//        requestMap.put(new AntPathRequestMatcher("/manager/**"), Arrays.asList(new SecurityConfig("ROLE_MANAGER")));
//        requestMap.put(new AntPathRequestMatcher("/qna/manager/**"), Arrays.asList(new SecurityConfig("ROLE_MANAGER")));
//        requestMap.put(new AntPathRequestMatcher("/admin/**"), Arrays.asList(new SecurityConfig("ROLE_ADMIN")));
//        requestMap.put(new AntPathRequestMatcher("/qna/admin/**"), Arrays.asList(new SecurityConfig("ROLE_ADMIN")));

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


    // 자원과 권한 관련된 데이터를 업데이트 할 때 db에 저장되고 그 시점에
    // reload를 호출하게 되면 db에서 그 자원을 가지고 와서 requestMap에 그 정보를 담아서 전달한다.(실시간 업데이트))
    // 적절한 사용 위치는 프로젝트별로 다를 수 있다.
    // 보통 새로 자원을 등록하는 로직, 자원을 삭제하는 로직에 아래 메서드를 사용할 수 있다.
    public void reload() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> reloadedMap = securityResourceService.getResourceList();
        Iterator<Map.Entry<RequestMatcher, List<ConfigAttribute>>> iterator = reloadedMap.entrySet().iterator();

        // 이전에 있던 자원 정보에 내용 삭제
        requestMap.clear();
        // 변경된 자원 정보 내용 추가
        while(iterator.hasNext()) {
            Map.Entry<RequestMatcher, List<ConfigAttribute>> entry = iterator.next();
            // db로부터 매핑된 정보를 채운다.
            requestMap.put(entry.getKey(), entry.getValue());
        }
    }
}
