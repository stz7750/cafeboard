package com.board.demo.admin.controller;


import com.board.demo.admin.service.AdminNotifiedService;
import com.board.demo.admin.vo.NotifiedVO;
import com.board.demo.member.serivce.MemberService;
import com.board.demo.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/management")
public class AdminRestController {

    @Autowired
    AdminNotifiedService adminNotifiedService;

    @Autowired
    MemberService memberService;

    @PostMapping("/saveNotified")
    public int saveNotified(@RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "content", required = false) String content,
                            @RequestParam(value = "author", required = false) String author,
                            @RequestParam(value = "notiNum", required = false) String notiNum,
                            @RequestParam(value = "modify_id", required = false) String modifyId,
                            @RequestParam("notiImg") MultipartFile notiImg,
                            @RequestParam(value = "starttime", required = false) String starttime,
                            @RequestParam(value= "endtime", required = false) String endtime){
        NotifiedVO vo = new NotifiedVO();
        vo.setTitle(title);
        vo.setContent(content);
        vo.setAuthor(author);
        vo.setNotiNum(notiNum);
        vo.setModifyId(modifyId);
        vo.setStarttime(starttime);
        vo.setEndtime(endtime);

        int upsert = adminNotifiedService.upsertNotified(vo,notiImg);
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
