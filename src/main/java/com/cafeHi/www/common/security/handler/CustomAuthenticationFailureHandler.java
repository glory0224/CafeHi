package com.cafeHi.www.common.security.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 기본 메세지
        String errorMessage = "Invalid Username or Password";

        // Exception type에 따라 다른 예외를 던진다.

        if(exception instanceof BadCredentialsException) {
            errorMessage = "Invalid Username or Password";
        } else if(exception instanceof InsufficientAuthenticationException) {
            errorMessage = "Invalid SeretKey or IpAddress or SessionId";
        }

        // exception 발생 시 기본적으로 돌아가는 페이지 설정
        setDefaultFailureUrl("/login?error=true&exception=" + exception.getMessage());

        super.onAuthenticationFailure(request, response, exception);
    }
}
