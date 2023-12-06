package com.cafeHi.www.common.security.filter;

import com.cafeHi.www.common.security.token.AjaxAuthenticationToken;
import com.cafeHi.www.member.dto.CustomMember;
import com.cafeHi.www.member.dto.MemberDTO;
import com.cafeHi.www.member.dto.MemberInfo;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.service.CustomUserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    public AjaxLoginProcessingFilter() {
        // Filter가 동작할 Url 설정
        super(new AntPathRequestMatcher("/api/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if(!isAjax(request)) {
            throw new IllegalArgumentException("Authentication is not supported");
        }


        MemberInfo memberInfo = objectMapper.readValue(request.getReader(), MemberInfo.class);

        // memberId, memberPw 가 빈 값이 아닌지 체크
        if(StringUtils.isEmpty(memberInfo.getMemberId()) || StringUtils.isEmpty(memberInfo.getMemberPw())) {
            throw new IllegalArgumentException("Username or Password is empty");
        }

        AjaxAuthenticationToken ajaxAuthenticationToken = new AjaxAuthenticationToken(memberInfo.getMemberId(), memberInfo.getMemberPw());

        return getAuthenticationManager().authenticate(ajaxAuthenticationToken);
    }

    // header 정보에 담긴 값과 같은지 여부로 판단
    // 실제로 사용시 클라이언트 쪽과 서버쪽이 서로 일치되도록 규약을 정하면 된다.
    private boolean isAjax(HttpServletRequest request) {
//XMLHTTPRequest
        // 여기서는 이와 같은 규약을 임의로 정했다.
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return true;
        }
            return false;
        }

    }
