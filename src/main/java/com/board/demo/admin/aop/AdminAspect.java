package com.board.demo.admin.aop;

import com.board.demo.admin.service.AdminLogService;
import com.board.demo.admin.vo.AdminLogVO;
import com.board.demo.admin.vo.NotifiedVO;
import com.board.demo.member.vo.MemberVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Aspect
@Component
public class AdminAspect {

    @Autowired
    private AdminLogService adminLogService;

    @Autowired
    private HttpSession httpSession;

    @Pointcut("execution(* com.board.demo.admin.controller.*.*(..))")
    public void controllerMethods() {}

    @After("controllerMethods()")
    public void afterControllerMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = joinPoint.getSignature().getName();
        System.out.println("AdminController method executed: " + methodName);
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = signature.getParameterNames();
        String id = (String) httpSession.getAttribute("loggedInUsername");
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                System.out.println(parameterNames[i] + ": " + args[i]);
                if(methodName.equals("dashBoard") && parameterNames[i].equals("m")){
                    Model m = (Model) args[i];
                    AdminLogVO log = new AdminLogVO();
                    log.setId(id);
                    log.setDescription(id+"님이 관리자페이지에 입장 하셨습니다.");
                    log.setRecordDate(LocalDateTime.now());
                    adminLogService.insertAdminLog(log);
                }else if(methodName.equals("findMember") && parameterNames[i].equals("memberId")){
                    String memberId = (String) args[i];
                    AdminLogVO log = new AdminLogVO();
                    log.setId(id);
                    log.setDescription(id+"님이 회원 : "+memberId+"님을 검색했습니다.");
                    log.setRecordDate(LocalDateTime.now());
                    adminLogService.insertAdminLog(log);
                }else if(methodName.equals("saveNotified") && parameterNames[i].equals("vo")){
                    NotifiedVO vo = (NotifiedVO) args[i];
                    String title = vo.getTitle();
                    String notiNum = vo.getNotiNum();
                    if(notiNum.isEmpty() || notiNum == null) {
                        AdminLogVO log = new AdminLogVO();
                        log.setId(id);
                        log.setDescription(id + "님이 공지글을 작성하였습니다. (제목: \"  "+title+"  \")");
                        log.setRecordDate(LocalDateTime.now());
                        adminLogService.insertAdminLog(log);
                    }else{
                        AdminLogVO log = new AdminLogVO();
                        log.setId(id);
                        log.setDescription(id + "님이 공지글을 수정하였습니다. (제목: \"  "+title+ " \")");
                        log.setRecordDate(LocalDateTime.now());
                        adminLogService.insertAdminLog(log);
                    }
                }else if(methodName.equals("updateNotifiedShowYn") && parameterNames[i].equals("showYn")){
                    String showYn = (String) args[i];
                    String title = null;
                    if(parameterNames[2].equals("title")) {
                         title = (String) args[2];
                    }
                    AdminLogVO log = new AdminLogVO();
                    log.setId(id);
                    if(showYn.equals("N")) {
                        log.setDescription(id+"님이 공지글 :"+title+"을 비공개 처리했습니다.");
                    }else{
                        log.setDescription(id+"님이 공지글 : "+title+"을 공개 처리하였습니다.");
                    }
                    log.setRecordDate(LocalDateTime.now());
                    adminLogService.insertAdminLog(log);
                }else if(methodName.equals("deleteNotification") && parameterNames[i].equals("title")){
                    String title = (String) args[i];
                    AdminLogVO log = new AdminLogVO();
                    log.setId(id);
                    log.setDescription(id+"님이 공지글 : "+title+"을 삭제 했습니다.");
                    log.setRecordDate(LocalDateTime.now());
                    adminLogService.insertAdminLog(log);
                }
            }
        } else {
            System.out.println("No arguments.");
        }
    }
}
