package com.board.demo.admin.controller;


import com.board.demo.admin.service.AdminLogService;
import com.board.demo.admin.service.AdminNotifiedService;
import com.board.demo.admin.vo.AdminLogVO;
import com.board.demo.admin.vo.NotifiedVO;
import com.board.demo.member.serivce.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminNotifiedService adminNotifiedService;

    @Autowired
    AdminLogService adminLogService;

    @Autowired
    MemberService memberService;

    @GetMapping("/dashBoard")
    public String dashBoard(Model m,
                            @RequestParam(name ="page" ,defaultValue = "1")int  page,
                            @RequestParam(name ="size",required = false, defaultValue = "5")int  size){

        Page<NotifiedVO> notifiedPaging = adminNotifiedService.getNotifiedPaging(page,size);
        Page<AdminLogVO> logPaging = adminLogService.getAdminLogPaging(page,size);
        m.addAttribute("notifiedList",notifiedPaging);
        m.addAttribute("logList",logPaging);
        return "admin/dashBoard";
    }

}
