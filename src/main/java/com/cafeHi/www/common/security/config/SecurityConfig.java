package com.cafeHi.www.common.security.config;


import com.cafeHi.www.common.security.provider.CustomAuthenticationProvider;
import com.cafeHi.www.member.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final CustomUserDetailService customUserDetailService;
	private final AuthenticationDetailsSource customAuthenticationDetailsSource;
	private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;
	private final AuthenticationFailureHandler customAuthenticationFailureHandler;
	
	public void configure(WebSecurity web) throws Exception {
		// 인증을 무시하기 위한 설정
		web.ignoring().antMatchers("/cafeHi/**","/js/**", "/css/**");
	}
	
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().disable()	// Post Mapping 할 때 시큐리티는 csrf를 체크하면서 값이 없으면 403 에러를 발생시키는데 그 기능을 꺼주는 설정
                .authorizeRequests()
                .antMatchers("/", "/CafeHi-Introduce", "/CafeHi-Membership",
						"/CafeHi-Event", "/CafeHi-Place", "/CafeHi-Menu", "common/login*").permitAll()	// "common/login*" login 뒤에 어떤 문자열이 찍히더라도 경로로 인식하고 permitAll 처리한다.
                .antMatchers("/CafeHi-MemberInfo").hasRole("USER")
                .antMatchers("/CafeHi-MemberInfoUpdate").hasRole("USER")
                .antMatchers("/CafeHi-MemberChangePassword").hasRole("USER")
                .antMatchers("/CafeHi-MemberInfoDelete").hasRole("USER")
                .antMatchers("/CafeHi-QnAWrite").hasRole("USER")                
                .antMatchers("/CafeHi-UpdateQnA").hasRole("USER")                
                .antMatchers("/CafeHi-MemberMyPage").hasRole("USER")
				.antMatchers("/CafeHi-MyMembership").hasRole("USER")
				.antMatchers("/CafeHi-MyPageQnA").hasRole("USER")
                .antMatchers("/CafeHi-MyPageCart").hasRole("USER")
                .antMatchers("/insertCart").hasRole("USER")
                .antMatchers("/modifyCart").hasRole("USER")
                .antMatchers("/CafehiOrder").hasRole("USER")
                .antMatchers("/CafeHi-MyPageOrderMenuList").hasRole("USER")

                .and()
                	.formLogin()
                    .loginPage("/login")	// 커스텀 login 페이지 이동
					.successHandler(customAuthenticationSuccessHandler)
					.failureHandler(customAuthenticationFailureHandler)
					.authenticationDetailsSource(customAuthenticationDetailsSource)
                    .permitAll()
                    .and()
					;
    }
	
	
	
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
    }

	@Bean
	public AuthenticationProvider authenticationProvider() {
		// 커스텀 AuthenticationProvider 생성
		return new CustomAuthenticationProvider(customUserDetailService, pwdEncoder());
	}

	@Bean
	public BCryptPasswordEncoder pwdEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	
}
