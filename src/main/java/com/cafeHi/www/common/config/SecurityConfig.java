package com.cafeHi.www.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.cafeHi.www.member.service.CustomUserDetailService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final CustomUserDetailService customUserDetailService;
	
	
	public void configure(WebSecurity web) throws Exception {
		// 인증을 무시하기 위한 설정
		web.ignoring().antMatchers("/cafeHi/**","/js/**", "/css/**");
	}
	
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().disable()	// Post Mapping 할 때 시큐리티는 csrf를 체크하면서 값이 없으면 403 에러를 발생시키는데 그 기능을 꺼주는 설정
                .authorizeRequests()
                .antMatchers("/", "/CafeHi-Introduce", "/CafeHi-Membership", "/CafeHi-Event", "/CafeHi-Place", "/CafeHi-Menu").permitAll()
                .antMatchers("/CafeHi-MemberInfo").hasRole("USER")
                .antMatchers("/CafeHi-MemberInfoUpdate").hasRole("USER")
                .antMatchers("/CafeHi-MemberInfoDelete").hasRole("USER")                
                .antMatchers("/CafeHi-QnAWrite").hasRole("USER")                
                .antMatchers("/CafeHi-UpdateQnA").hasRole("USER")                
                .antMatchers("/CafeHi-MemberMyPage").hasRole("USER")
				.antMatchers("/CafeHi-MyMembership").hasRole("USER")
				.antMatchers("/CafeHi-MyPageQnA").hasRole("USER")
                .antMatchers("/CafeHi-MyPageCart").hasRole("USER")
                .antMatchers("/CafehiOrder").hasRole("USER")
                .antMatchers("/CafeHi-MyPageOrderMenuList").hasRole("USER")

                .and()
                .formLogin()
                    .loginPage("/login")	// 커스텀 login 페이지 이동
                    .defaultSuccessUrl("/")	// 로그인 성공 시 이동 페이지
//                    .loginProcessingUrl("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
                    .and()
                .logout()
				 .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
				/* .logoutUrl("/logout") */
                	.logoutSuccessUrl("/login")
                	.invalidateHttpSession(true)	// 세션 초기화
                    .permitAll();
    }
	
	
	
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // add our Users for in memory authentication
        // auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
        auth.userDetailsService(customUserDetailService);
    }
	
	@Bean
	public BCryptPasswordEncoder pwdEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	
}
