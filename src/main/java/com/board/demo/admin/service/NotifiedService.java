package com.board.demo.admin.service;


import com.board.demo.admin.mapper.NotifiedMapper;
import com.board.demo.admin.vo.NotifiedVO;
import com.board.demo.content.vo.ContentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifiedService {

    @Autowired
    NotifiedMapper notifiedMapper;

    public int UpsertNotified(NotifiedVO vo){
        return notifiedMapper.insertOrUpdateNotified(vo);
    }


    public Page<NotifiedVO> getNotifiedPaging(int page, int size){
        final int start = (page -1)*size;
        final int count = notifiedMapper.cntNotified(start,size);

        List<NotifiedVO> list = notifiedMapper.selectNotified(start,size);
        Page<NotifiedVO> result = new PageImpl<NotifiedVO>(list, PageRequest.of(page -1,size),count);
        return result;
    }

    public void updateNotifiedShowYn(int id, String showYn) {
        notifiedMapper.updateNotifiedShowYn(id, showYn);
    }

}
