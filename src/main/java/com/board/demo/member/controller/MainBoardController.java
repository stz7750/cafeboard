package com.board.demo.member.controller;


import com.board.demo.admin.vo.NotifiedVO;
import com.board.demo.content.service.ContentService;
import com.board.demo.content.vo.ContentVO;
import com.board.demo.global.TableModule;
import com.board.demo.member.serivce.MemberService;
import com.board.demo.member.vo.AjaxPageResponse;
import com.board.demo.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/hello")
public class MainBoardController {

    @Autowired
    MemberService service;

    @Autowired
    ContentService contentService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    //메인 페이지
    @GetMapping("main")
    public String main(Model m) throws Exception{
        List<NotifiedVO> mainNotifiedList = service.notifiedMain();
        m.addAttribute("RecContent",contentService.getMostRecContent());
        m.addAttribute("viewContent",contentService.getMostViewContent());
        m.addAttribute("mainNotified",mainNotifiedList);
        m.addAttribute("notiPath",uploadDir);
        return "member/main";
    }

    @GetMapping("/getRankRecData")
    @ResponseBody
    public List<ContentVO> getRankRecData() throws Exception {
        List<ContentVO> rankRecData = contentService.getRankingRec();
        return rankRecData;
    }

    /*htmx*/
    @GetMapping("/showAlert")
    @ResponseBody
    public String showAlert() {
        return "새로고침 되었습니다.";
    }

    @PostMapping("/getTodayRecData")
    @ResponseBody
    public String getTodayRecData(){
        List<ContentVO> todayRecData = contentService.getMostRecContent();
        return TableModule.createTable(todayRecData,"recTable");
    }

    @PostMapping("/getTodayViewData")
    @ResponseBody
    public String getTodayViewData(){
        List<ContentVO> todayViewData = contentService.getMostViewContent();
        return TableModule.createTable(todayViewData,"viewTable");
    }
    @PostMapping("/getWeekRecData")
    @ResponseBody
    public String getWeeklyRecData() {
        List<ContentVO> weeklyRecData = contentService.getWeekMostRec();
        return TableModule.createTable(weeklyRecData,"recTable");
    }

    @PostMapping("/getWeekViewData")
    @ResponseBody
    public String getWeekViewData(){
        List<ContentVO> weeklyViewData = contentService.getWeekMostView();
        return TableModule.createTable(weeklyViewData,"viewTable");
    }

    @PostMapping("/getCategoryByContent")
    @ResponseBody
    public List<ContentVO> getCategoryByContent(@RequestParam("category")String category){
        List<ContentVO> list = contentService.getCategoryByContent(category);
        return list;
    }


    //회원가입 페이지
    @GetMapping("entry")
    public String entry(){
        return "member/entry";
    }

    //회원 insert
    @PostMapping("/signUp")
    public String welcome(MemberVO vo){
        String id = vo.getId();
        String name = vo.getName();
        String password = vo.getPassword();
        MemberVO member = service.findByUserinfo(id);

        boolean passwordLength = password.length() >= 8 && password.length() <= 15;
        boolean username = name.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");

        if(member == null && !username && passwordLength){
            service.addMember(vo);
            return "success";
        }else{
            return "fail";
        }

    }

    //로그인
    @GetMapping("login")
    public String login(){
        return "/member/login";
    }

    //접근 불가 시 커스텀 에러 페이지
    @GetMapping("/forbidden")
    public String forbidden(){
        return "/commons/error";
    }


    //로그아웃
    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // 세션을 무효화하여 사용자 정보를 삭제합니다.
        request.getSession().invalidate();

        // 로그아웃 후, 홈페이지 또는 로그인 페이지로 리다이렉트합니다.
        return "redirect:/hello/login";
    }



    //마이페이지
    @GetMapping("myPage")
    public String mypage(HttpServletRequest request, Model m) throws Exception{
            // 현재 세션에서 로그인한 유저의 아이디를 가져옵니다.
            String loggedInUsername = (String) request.getSession().getAttribute("loggedInUsername");
            // LocalDateTime로 현재 날짜와 시간 구하기
            LocalDateTime currentDateTime = LocalDateTime.now();
            // 요일 정보를 한글로 표시하기 위해 DateTimeFormatter 설정
            DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("E");
            String currentDayOfWeek = currentDateTime.format(dayOfWeekFormatter);
            // 로그인한 회원의 정보를 가져옵니다.
            MemberVO member = service.findByUserinfo(loggedInUsername);
            // 로그인한 회원의 작성 게시물을 가져옵니다.
            List<ContentVO> memberContent = service.getMemberContent(loggedInUsername);
            // 로그인한 회원의 오늘기준 게시물을 카운트합니다.
            int tCntContent = service.todayMemberContent(loggedInUsername);
            // 로그인한 회원의 한달 기준 게시물을 카운트합니다.
            int mCntContent = service.monthMemberContent(loggedInUsername);
            // 로그인한 회원의 오늘 기준 전체 추천 수를 가져옵니다.
            int tCntRec = service.todayMemberRec(loggedInUsername);
            // 로그인한 회원의 한달 기준 전체 추천 수를 가져옵니다.
            int mCntRec = service.monthMemberRec(loggedInUsername);
            // 필요한 멤버 정보를 모델에 추가하여 뷰로 전달합니다.
            m.addAttribute("todayContent",tCntContent);
            m.addAttribute("todayRec",tCntRec);
            m.addAttribute("monthContent",mCntContent);
            m.addAttribute("monthRec",mCntRec);
            m.addAttribute("currentDayOfWeek",currentDayOfWeek);
            m.addAttribute("ContentCnt",service.memberContentCnt(loggedInUsername));
            m.addAttribute("recCount",service.memberRecCount(loggedInUsername));
            m.addAttribute("memberContent",memberContent);
            m.addAttribute("member", member);
            m.addAttribute("addr1",member.getAddr1());
            m.addAttribute("addr2",member.getAddr2());

            return "member/myPage";
    }

    @PostMapping("myPaging")
    public ResponseEntity<AjaxPageResponse<ContentVO>> getMemberContentPaging(@RequestParam(name ="page" ,defaultValue = "1")int  page,
                                                                          @RequestParam(name ="size",required = false, defaultValue = "10")int  size,
                                                                          HttpServletRequest request){
        String loggedInUsername = (String) request.getSession().getAttribute("loggedInUsername");

        Page<ContentVO>  ContentPaging = service.getMemberContentPaging(page,size,loggedInUsername);
        AjaxPageResponse<ContentVO> response = new AjaxPageResponse<>(0, "success", ContentPaging);
        return new ResponseEntity<AjaxPageResponse<ContentVO>>(response, HttpStatus.OK);
    }

    @PostMapping("/infoModify")
    public String memberInfoModify(MemberVO vo,HttpServletRequest request){
        /*String id = (String) request.getSession().getAttribute("loggedInUsername");*/
        service.memberInfoModify(vo);
        return "redirect:/hello/myPage";
    }

    @DeleteMapping("/removeMemberContent")
    @ResponseBody
    public String deleteMemberContent(@RequestParam("ids")List<Integer> ids,HttpServletRequest request,Model m) {
        String author = (String) request.getSession().getAttribute("loggedInUsername");

        String deleteResult = service.deleteMemberContent(ids, author);
        if (deleteResult.equals("success")) {
            return "success";
        } else {
            return "fail";
        }
    }

}
