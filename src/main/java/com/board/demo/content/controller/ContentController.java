package com.board.demo.content.controller;


import com.board.demo.content.service.ContentService;
import com.board.demo.content.vo.ContentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    ContentService contentService;

    //자유게시판
    @GetMapping("/boardList")
    public String boardList(Model m){
        List<ContentVO> vo = contentService.selectContentList();

        m.addAttribute("boardContent",contentService.selectContentList());
        return "content/boardList";
    }
}
