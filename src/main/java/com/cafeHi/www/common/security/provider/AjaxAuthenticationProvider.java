package com.cafeHi.www.common.security.provider;

import com.cafeHi.www.common.security.service.CustomUser;
import com.cafeHi.www.common.security.token.AjaxAuthenticationToken;
import com.cafeHi.www.common.security.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class AjaxAuthenticationProvider implements AuthenticationProvider {
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

        // 인증에 성공하면 AuthenticationToken 을 생성한다.
        AjaxAuthenticationToken authenticationToken = new AjaxAuthenticationToken(customUser.getMemberInfo(), null, customUser.getAuthorities());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AjaxAuthenticationToken.class);
    }
}
