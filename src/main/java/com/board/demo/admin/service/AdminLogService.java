package com.board.demo.admin.service;


import com.board.demo.admin.mapper.AdminLogMapper;
import com.board.demo.admin.vo.AdminLogVO;
import com.board.demo.admin.vo.NotifiedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminLogService {

    @Autowired
    AdminLogMapper logMapper;

    @Autowired
    SessionRegistry sessionRegistry;

    public Page<AdminLogVO> getAdminLogPaging(int page,int size){
        final int start = (page -1)*size;
        final int count = logMapper.cntAdminLog();

        List<AdminLogVO> list = logMapper.selectAdminLog(start,size);
        Page<AdminLogVO> result = new PageImpl<AdminLogVO>(list,PageRequest.of(page -1,size),count);
        return result;
    }


    public int insertAdminLog(AdminLogVO vo){
        return logMapper.insertAdminLog(vo);
    }


    // spring security sessionRegistry 세션들을 저장 해놓는 역할을 한다.
    public int getLoggedInUserCount() {
        return sessionRegistry.getAllPrincipals().size();
    }
}
