package com.board.demo.admin.controller;


import com.board.demo.admin.service.NotifiedService;
import com.board.demo.admin.vo.NotifiedVO;
import com.board.demo.member.serivce.MemberService;
import com.board.demo.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    NotifiedService notifiedService;

    @Autowired
    MemberService memberService;

    @GetMapping("/dashBoard")
    public String dashBoard(Model m,
                            @RequestParam(name ="page" ,defaultValue = "1")int  page,
                            @RequestParam(name ="size",required = false, defaultValue = "10")int  size){
        Page<NotifiedVO> notifiedPaging = notifiedService.getNotifiedPaging(page,size);
        m.addAttribute("notifiedList",notifiedPaging);
        return "admin/dashBoard";
    }

}
