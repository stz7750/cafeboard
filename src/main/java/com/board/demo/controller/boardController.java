package com.board.demo.controller;


import com.board.demo.serivce.MemberService;
import com.board.demo.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("hello")
public class boardController {

    @Autowired
    MemberService service;

    //메인 페이지
    @GetMapping("main")
    public String main(Model m){
        m.addAttribute("name","관리자");
        List<MemberVO> members = service.getMember();
        m.addAttribute("members",members);
        return "main";
    }

    @GetMapping("a")
    public String pageA(){
        return "pageA";
    }

    //회원가입 페이지
    @GetMapping("entry")
    public String entry(){
        return "entry";
    }

    //회원 insert
    @PostMapping("signUp")
    public String welcome(Model m,MemberVO vo){
        //회원가입 시 입력했던 정보를 insert 합니다.
        service.addMember(vo);
        return "main";
    }

    //로그인
    @GetMapping("login")
    public String login(){
        return "login";
    }

    //마이페이지
    @GetMapping("myPage")
    public String mypage(HttpServletRequest request, Model model){
        // HttpServletRequest 객체를 이용하여 세션에서 사용자 이름을 가져옵니다.
        String MemberId = (String) request.getSession().getAttribute("id");

        // 사용자 이름을 기반으로 데이터베이스에서 해당 멤버 정보를 조회합니다.
        MemberVO member = service.findByUserinfo(MemberId);

        model.addAttribute("member", member);
        return "myPage";
    }

    //자유게시판
    @GetMapping("boardList")
    public String boardList(){
        return "boardList";
    }

}
