package com.board.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = Controller.class) //컨트롤러 어노테이션에 모두 적용합니다.
public class GlobalControllerAdvice {
    // 로그인한 유저에 대해서 username 속성을 가쟈와 모델에 추가합니다.
    @ModelAttribute
    public void addLoggedInUsernameToModel(Model model, HttpServletRequest request) {
        String loggedInUsername = (String) request.getSession().getAttribute("loggedInUsername");
        model.addAttribute("loggedInUsername", loggedInUsername);
    }
}

