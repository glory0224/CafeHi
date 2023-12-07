package com.cafeHi.www.common.security.provider;

import com.cafeHi.www.common.security.common.FormWebAuthenticationDetails;
import com.cafeHi.www.common.security.service.CustomUser;
import com.cafeHi.www.common.security.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class FormAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailService customUserDetailService;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        CustomUser customUser = (CustomUser)customUserDetailService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, customUser.getPassword())) {
            throw new BadCredentialsException("BadCredentialsException");
        }

        // custom으로 구현한 Details 반환
        FormWebAuthenticationDetails formWebAuthenticationDetails = (FormWebAuthenticationDetails) authentication.getDetails();

        String secretKey = formWebAuthenticationDetails.getSecretKey();
        String customerIpAddress = formWebAuthenticationDetails.getCustomerIpAddress();
        String customerSessionId = formWebAuthenticationDetails.getCustomerSessionId();

        // 클라이언트로부터 전달받는 데이터 검증
        if(secretKey == null || !"secret".equals(secretKey)) {
            throw new InsufficientAuthenticationException("secretKey is not Authentication");
        } else if(customerIpAddress == null || !"127.0.0.1".equals(customerIpAddress)) {
            throw new InsufficientAuthenticationException("customerIpAddress is not Authentication");
        } else if(customerSessionId == null || !"ADE1231415".equals(customerSessionId)) {
            throw new InsufficientAuthenticationException("customerSessionId is not Authentication");
        }

        // 인증에 성공하면 AuthenticationToken 을 생성한다.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUser.getMemberInfo(), null, customUser.getAuthorities());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
