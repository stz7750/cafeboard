package com.board.demo.content.controller;


import com.board.demo.content.service.ContentService;
import com.board.demo.content.vo.ContentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    ContentService contentService;

    //자유게시판
    @GetMapping("/boardList")
    public String boardList(Model m,
                            @RequestParam(name ="page" ,defaultValue = "1")int  page,
                            @RequestParam(name ="size",required = false, defaultValue = "10")int  size){

        Page<ContentVO> boardContent = contentService.getContentPaging(page,size);
        m.addAttribute("boardContent",boardContent);
        return "content/boardList";
    }

    @GetMapping("/boardRegist")
    public String boardRegist(){
        return "content/boardRegist";
    }

    @PostMapping("/save")
    public String saveContent(ContentVO contentVO) {
        contentService.addBoardContent(contentVO);
        return "redirect:/content/boardList";
    }

    @GetMapping("/modify")
    public String modifycontent(@RequestParam("id") int id,@RequestParam("author") String author,Model m){
        ContentVO vo = contentService.getMemberContent(id,author);
        m.addAttribute("modifyContent", vo);
        return "/content/boardModify";
    }

    @PutMapping("/modify")
    @ResponseBody
    public String modifyContent(@RequestParam("id") int id, @RequestParam("author") String author, ContentVO contentVO) {
        boolean isUpdated = contentService.updateContent(id, author, contentVO);
        if (isUpdated) {
            return "success";
        } else {
            return "fail";
        }
    }

    @GetMapping("/boardView")
    public String viewContent(@RequestParam("id")int id, Model m){
        ContentVO view = contentService.getByContentId(id);
        if(view != null){
            m.addAttribute("view", view);
            return "/content/boardView";
        }else{
            m.addAttribute("message","해당 게시글을 찾을 수 없습니다.");
            return "/content/boardList";
        }
    }
    @PostMapping("/modifyRecCount")
    @ResponseBody
    public String modifyRecCount(@RequestParam("author")String author, @RequestParam("id")int id){
        String addRecommendation = contentService.addRecommendation(author,id);

        if(addRecommendation.equals("success")){
            return "success";
        }else if(addRecommendation.equals("alreadyRec")){
            return "alreadyRec";
        } else{
            return "Error";
        }
    }

    @GetMapping("/searchContent")
    public String searchContent(@RequestParam("searchValue")String searchValue,
                                @RequestParam("searchType")String searchType,
                                @RequestParam(name ="page" ,required = false, defaultValue = "1")int page,
                                @RequestParam(name ="size",required = false, defaultValue = "10")int size,
                                Model m){
        Page<ContentVO> searchContent = contentService.getSearchPaging(searchType,searchValue,page,size);
        m.addAttribute("searchContent", searchContent);
        return "/content/boardSearch";
    }
}
