package com.cafeHi.www.common.security.config;


import com.cafeHi.www.common.security.common.FormAuthenticationDetailSource;
import com.cafeHi.www.common.security.factory.UrlResourcesMapFactoryBean;
import com.cafeHi.www.common.security.filter.PermitAllFilter;
import com.cafeHi.www.common.security.handler.FormAccessDeniedHandler;
import com.cafeHi.www.common.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import com.cafeHi.www.common.security.provider.FormAuthenticationProvider;
import com.cafeHi.www.common.security.service.CustomUserDetailService;
import com.cafeHi.www.resources.service.SecurityResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.sql.CallableStatement;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(0)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final CustomUserDetailService customUserDetailService;
	private final FormAuthenticationDetailSource customAuthenticationDetailsSource;

	private final AuthenticationSuccessHandler formAuthenticationSuccessHandler; //스프링 component로 등록된 이름으로 필드명을 선언해줘야 제대로 생성자 주입이 가능하다.
	private final AuthenticationFailureHandler formAuthenticationFailureHandler;

	private final SecurityResourceService securityResourceService;

	// 인증이나 인가가 필요없는 자원
	private String[] permitAllResources = {"/", "/CafeHi-Introduce", "/CafeHi-Membership",
			"/CafeHi-Event", "/CafeHi-Place", "/CafeHi-Menu","/CafeHi-Signup", "common/login*"};

	public void configure(WebSecurity web) throws Exception {
		// 인증을 무시하기 위한 설정
		web.ignoring().antMatchers("/cafeHi/**","/js/**", "/css/**");
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().disable()	// Post Mapping 할 때 시큐리티는 csrf를 체크하면서 값이 없으면 403 에러를 발생시키는데 그 기능을 꺼주는 설정
                .authorizeRequests()
//                .antMatchers("/", "/CafeHi-Introduce", "/CafeHi-Membership",
//						"/CafeHi-Event", "/CafeHi-Place", "/CafeHi-Menu","/CafeHi-Signup", "common/login*").permitAll()	// "common/login*" login 뒤에 어떤 문자열이 찍히더라도 경로로 인식하고 permitAll 처리한다.
//                .antMatchers("/CafeHi-MemberInfo").hasRole("USER")
//                .antMatchers("/CafeHi-MemberInfoUpdate").hasRole("USER")
//                .antMatchers("/CafeHi-MemberChangePassword").hasRole("USER")
//                .antMatchers("/CafeHi-MemberInfoDelete").hasRole("USER")
//                .antMatchers("/CafeHi-QnAWrite").hasRole("USER")
//                .antMatchers("/CafeHi-UpdateQnA").hasRole("USER")
//                .antMatchers("/CafeHi-MemberMyPage").hasRole("USER")
//				.antMatchers("/CafeHi-MyMembership").hasRole("USER")
//				.antMatchers("/CafeHi-MyPageQnA").hasRole("USER")
//                .antMatchers("/CafeHi-MyPageCart").hasRole("USER")
//                .antMatchers("/insertCart").hasRole("USER")
//                .antMatchers("/modifyCart").hasRole("USER")
//                .antMatchers("/CafehiOrder").hasRole("USER")
//                .antMatchers("/CafeHi-MyPageOrderMenuList").hasRole("USER")
				.antMatchers("/member/**").hasRole("USER")
				.antMatchers("/qna/member/**").hasRole("USER")
				.antMatchers("/manager/**").hasRole("MANAGER")
				.antMatchers("/qna/manager/**").hasRole("MANAGER")
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/qna/admin/**").hasRole("ADMIN")


		.and()
                	.formLogin()
                    .loginPage("/login")	// 커스텀 login 페이지 이동
					.successHandler(formAuthenticationSuccessHandler)
					.failureHandler(formAuthenticationFailureHandler)
					.authenticationDetailsSource(customAuthenticationDetailsSource)
                    .permitAll()
		.and()
					.exceptionHandling()
					.accessDeniedHandler(accessDeniedHandler())
		.and()
				.addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class)

		;

    }

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
    }

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		FormAccessDeniedHandler accessDeniedHandler = new FormAccessDeniedHandler();
		accessDeniedHandler.setErrorPage("/denied");
		return accessDeniedHandler;
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		// 커스텀 AuthenticationProvider 생성
		return new FormAuthenticationProvider(customUserDetailService, pwdEncoder());
	}

	@Bean
	public BCryptPasswordEncoder pwdEncoder() {
		return new BCryptPasswordEncoder();
	}

	// FilterSecurityInterceptor 를 custom 방식으로 생성
	@Bean
//	public FilterSecurityInterceptor customFilterSecurityInterceptor() throws Exception {
	public PermitAllFilter customFilterSecurityInterceptor() throws Exception {

//		FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
		// 커스텀한 PermitAllFilter로 교체
		PermitAllFilter filterSecurityInterceptor = new PermitAllFilter(permitAllResources);
		filterSecurityInterceptor.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
		filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased());
		filterSecurityInterceptor.setAuthenticationManager(authenticationManagerBean());

		return filterSecurityInterceptor;
	}
	@Bean
	public AccessDecisionManager affirmativeBased() {
		AffirmativeBased affirmativeBased = new AffirmativeBased(getAccessDecisionVoters());
		return affirmativeBased;
	}

	private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
		return Arrays.asList(new RoleVoter());
	}

	@Bean
	public FilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() throws Exception {
		return new UrlFilterInvocationSecurityMetadataSource(urlResourcesMapFactoryBean().getObject(), securityResourceService);
	}

	private UrlResourcesMapFactoryBean urlResourcesMapFactoryBean() {

		UrlResourcesMapFactoryBean urlResourcesMapFactoryBean = new UrlResourcesMapFactoryBean();
		urlResourcesMapFactoryBean.setSecurityResourceService(securityResourceService);

		return urlResourcesMapFactoryBean;
	}

}
