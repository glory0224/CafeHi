package com.cafeHi.www.common.security.filter;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PermitAllFilter extends FilterSecurityInterceptor {
    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";
    private boolean observeOncePerRequest = true;

    private List<RequestMatcher> permitAllRequestMatchers = new ArrayList<>();

    public PermitAllFilter(String ...permitAllResources) {

        for (String resource : permitAllResources) {
            permitAllRequestMatchers.add(new AntPathRequestMatcher(resource));
        }

    }

    // 인가처리전에 PermitAllFilter 적용

    @Override
    protected InterceptorStatusToken beforeInvocation(Object object) {

        boolean permilAll = false;
        // Object 타입의 매개변수를 사용하기 때문에 형변환으로 request 정보를 뺀다
        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        for(RequestMatcher requestMatcher : permitAllRequestMatchers) {
            // 일치하는경우 인가처리할 필요가 없는 자원으로 판단
            if(requestMatcher.matches(request)) {
                permilAll = true;
                break;
            }
        }

        if (permilAll) {
            return null;    // FilterSecurityInterceptor 로 전달하지 않고(권한 심사 하지 않음) null 바로 반환
        }

        return super.beforeInvocation(object);
    }

    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        if (isApplied(filterInvocation) && this.observeOncePerRequest) {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
            return;
        }
        // first time this request being called, so perform security checking
        if (filterInvocation.getRequest() != null && this.observeOncePerRequest) {
            filterInvocation.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
        }

//        if((filterInvocation.getRequest() != null)
//                && (filterInvocation.getRequest().getAttribute(FILTER_APPLIED) != null)
//                && observeOncePerRequest) {
//            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
//        } else {
//            if(filterInvocation.getRequest() != null && observeOncePerRequest) {
//                filterInvocation.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
//            }
//        }

        InterceptorStatusToken token = beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        }
        finally {
            super.finallyInvocation(token);
        }
        super.afterInvocation(token, null);
    }

    private boolean isApplied(FilterInvocation filterInvocation) {
        return (filterInvocation.getRequest() != null)
                && (filterInvocation.getRequest().getAttribute(FILTER_APPLIED) != null);
    }


}
