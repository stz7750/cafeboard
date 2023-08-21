package com.board.demo.admin.controller;


import com.board.demo.admin.service.AdminNotifiedService;
import com.board.demo.admin.vo.NotifiedVO;
import com.board.demo.member.serivce.MemberService;
import com.board.demo.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management")
public class AdminRestController {

    @Autowired
    AdminNotifiedService adminNotifiedService;

    @Autowired
    MemberService memberService;

    @PostMapping("/saveNotified")
    public int saveNotified(@RequestBody NotifiedVO vo){
        int upsert = adminNotifiedService.upsertNotified(vo);
        if(upsert > 0){
            return upsert;
        }else{
           return -1;
        }
    }

    @PostMapping("/findMember")
    public MemberVO findMember(@RequestParam("memberId")String memberId){
        MemberVO memberInfo = memberService.findByUserinfo(memberId);
        return memberInfo;
    }

    @PostMapping("/updateNotifiedShowYn")
    public void updateNotifiedShowYn(@RequestParam("id")int id,@RequestParam("showYn")String showYn,@RequestParam("title")String title){
        adminNotifiedService.updateNotifiedShowYn(id, showYn);
    }

    @DeleteMapping("/deleteNotification")
    public String deleteNotification(@RequestParam("id") int id, @RequestParam("title")String title)throws Exception{
       boolean delete = adminNotifiedService.deleteNotification(id);
       if(delete == true){
           return "true";
       }else{
           return "false";
       }
    }
}
