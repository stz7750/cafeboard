package com.board.demo.config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.demo.member.serivce.MemberService;
import com.board.demo.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;

@Component
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 인증 정보로부터 사용자 아이디를 가져옵니다.
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();

        // 권한 목록을 가져옵니다.
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // 권한 목록에서 첫 번째 권한을 가져옵니다.
        String role = authorities.iterator().next().getAuthority();
        // 아이디를 세션에 저장합니다.
        request.getSession().setAttribute("loggedInUsername", username);
        request.getSession().setAttribute("loggedInUserRole",role);

        // 로그인 성공 후의 이동 경로를 설정합니다.
        response.sendRedirect("/hello/main");


    }
}

