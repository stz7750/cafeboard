package com.board.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BoardUserDetailsService boardUserDetailsService;

    @Autowired
    private MemberAuthenticationSuccessHandler authenticationSuccessHandler;

    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/hello/login","/hello/entry","/hello/signUp").permitAll() // 로그인 페이지는 모든 사용자에게 허용
                .antMatchers("/static/css/**", "/static/js/**", "/static/img/**").permitAll() // CSS, JS, 이미지 파일은 모든 사용자에게 허용
                .antMatchers(uploadDir + "/**").permitAll()
                .antMatchers("/admin/dashBoard").hasAnyRole("ROLE_ADMIN")
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

        http
                .sessionManagement()
                .maximumSessions(1) // 동시 로그인 허용 수
                .maxSessionsPreventsLogin(false) // 중복 로그인 허용
                .expiredUrl("/hello/login") // 세션이 만료된 경우 이동할 URL
                .sessionRegistry(sessionRegistry());
    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // boardUserDetailsService를 사용하여 사용자 인증 처리
        auth.userDetailsService(boardUserDetailsService);
    }


    //시큐리티 하위폴더 인증 인가를 허가합니다.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/css/**", "/static/js/**", "/static/img/**",uploadDir + "/**");
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
