package com.board.demo.member.controller;


import com.board.demo.member.serivce.MemberService;
import com.board.demo.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("hello")
public class MainBoardController {

    @Autowired
    MemberService service;


    //메인 페이지
    @GetMapping("main")
    public String main(Model m,HttpServletRequest request){
        // MemberAuthenticationSuccess 를 이용해서 세션에 저장한 멤버의 아이디를 request를 이용해서 가져옵니다
        String loggedInUsername = (String) request.getSession().getAttribute("loggedInUsername");

        // 가져온 아이디 값을 모델에 추가하여 뷰로 전달합니다.
        m.addAttribute("loggedInUsername", loggedInUsername);
        return "member/main";
    }


    //회원가입 페이지
    @GetMapping("entry")
    public String entry(){
        return "member/entry";
    }

    //회원 insert
    @PostMapping("signUp")
    public String welcome(Model m,MemberVO vo){
        //회원가입 시 입력했던 정보를 insert 합니다.
        service.addMember(vo);
        return "member/login";
    }

    //로그인
    @GetMapping("login")
    public String login(){
        return "/member/login";
    }

    //로그아웃
    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // 세션을 무효화하여 사용자 정보를 삭제합니다.
        request.getSession().invalidate();

        // 로그아웃 후, 홈페이지 또는 로그인 페이지로 리다이렉트합니다.
        return "redirect:/hello/login";
    }



    //마이페이지
    @GetMapping("myPage")
    public String mypage(HttpServletRequest request, Model m){
        String loggedInUsername = (String) request.getSession().getAttribute("loggedInUsername");
        try {
            // MemberService를 사용하여 해당 멤버 정보를 조회합니다.
            MemberVO member = service.findByUserinfo(loggedInUsername);

            // 조회한 멤버 정보를 모델에 추가하여 뷰로 전달합니다.
            m.addAttribute("member", member);

            return "member/myPage";
        } catch (RuntimeException e) {
            // 조회 결과가 없는 경우 에러 메시지를 처리하거나 다른 방법으로 처리합니다.
            m.addAttribute("errorMessage", "멤버 정보를 찾을 수 없습니다. \n 다시 로그인 해주세요");

            return "member/login";
        }
    }


}
