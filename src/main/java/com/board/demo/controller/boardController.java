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

import java.util.List;

@Controller
@RequestMapping("hello")
public class boardController {

    @Autowired
    MemberService service;

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

    @GetMapping
    public String login(){
        return "entry";
    }

    @PostMapping("signUp")
    public String welcome(Model m,MemberVO vo){
        service.addMember(vo);
        return "main";
    }

}
