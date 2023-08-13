package com.board.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BoardUserDetailsService boardUserDetailsService;

    @Autowired
    private MemberAuthenticationSuccessHandler authenticationSuccessHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/hello/login").permitAll() // 로그인 페이지는 모든 사용자에게 허용
                .antMatchers("/static/css/**", "/static/js/**", "/static/img/**").permitAll() // CSS, JS, 이미지 파일은 모든 사용자에게 허용
                .anyRequest().authenticated() // 로그인이 필요한 요청은 인증 필요
                .and()
                .formLogin()
                .loginPage("/hello/login") // 커스텀 로그인 페이지
                .successHandler(authenticationSuccessHandler)
                .and()
                .logout()
                .logoutSuccessUrl("/hello/login") // 로그아웃 시 이동할 URL
                .and()
                .csrf().disable(); // CSRF 보안 설정 비활성화 (간단한 예제에서 사용하기 위해)
    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // boardUserDetailsService를 사용하여 사용자 인증 처리
        auth.userDetailsService(boardUserDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/css/**", "/static/js/**", "/static/img/**");
    }

}

