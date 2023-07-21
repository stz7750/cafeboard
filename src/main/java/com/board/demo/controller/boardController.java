package com.board.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("hello")
public class boardController {
    @GetMapping("main")
    public String main(Model m){
        m.addAttribute("name","관리자");
        return "main";
    }

    @GetMapping("a")
    public String pageA(){
        return "pageA";
    }
}
