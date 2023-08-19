package com.board.demo.admin.controller;


import com.board.demo.admin.service.NotifiedService;
import com.board.demo.admin.vo.NotifiedVO;
import com.board.demo.member.serivce.MemberService;
import com.board.demo.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management")
public class AdminRestController {

    @Autowired
    NotifiedService notifiedService;

    @Autowired
    MemberService memberService;

    @PostMapping("/saveNotified")
    public int saveNotified(@RequestBody NotifiedVO vo){
        int upsert = notifiedService.upsertNotified(vo);
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
    public void updateNotifiedShowYn(@RequestParam("id")int id,@RequestParam("showYn")String showYn){
        notifiedService.updateNotifiedShowYn(id, showYn);
    }
}
