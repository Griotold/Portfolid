package com.example.demo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.login.LoginFailHandler;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class SecurityConfig{
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public LoginFailHandler loginFailHandler(){
        return new LoginFailHandler();
    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)
		throws Exception{
		
		http.authorizeHttpRequests()
			.antMatchers("/", "/sample/all").permitAll()
			.antMatchers("/sample/member", "/user/mypage.do").hasAnyRole("USER", "ADMIN")
			.antMatchers("/sample/admin").hasRole("ADMIN");
		
		http.formLogin()
			.loginPage("/login")	// controller mapping
			.loginProcessingUrl("/login_proc") // th:action="@{login_proc}"
			.failureHandler(loginFailHandler())
			.defaultSuccessUrl("/")
			.permitAll();
		
		http.csrf().disable();
		http.logout();
		
		return http.build();
	}
	
	 @Bean
	// js, css, image 설정은 보안 설정의 영향 밖에 있도록 만들어주는 설정.
	 public WebSecurityCustomizer webSecurityCustomizer() {
	        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());

	 }
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) 
//			throws Exception {
//		auth.inMemoryAuthentication().withUser("user1")
//			.password("$2a$10$xTpHU4wN5.4uZNtArqAbC.Lj39//uCHLKXEb0w6Ra1PF8JMn9WnuG")
//			.roles("USER");
//			
//	}
}