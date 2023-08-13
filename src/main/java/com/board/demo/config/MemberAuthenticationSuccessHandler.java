package com.board.demo.config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 인증 정보로부터 사용자 아이디를 가져옵니다.
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();

        // 아이디를 세션에 저장합니다.
        request.getSession().setAttribute("loggedInUsername", username);

        // 로그인 성공 후의 이동 경로를 설정합니다.
        response.sendRedirect("/hello/main"); // 로그인 성공 시 이동할 URL을 지정해주세요.
    }
}

