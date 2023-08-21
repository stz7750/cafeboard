package com.board.demo.admin.service;


import com.board.demo.admin.mapper.AdminNotifiedMapper;
import com.board.demo.admin.vo.NotifiedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdminNotifiedService {

    @Autowired
    AdminNotifiedMapper notifiedMapper;

    public int upsertNotified(NotifiedVO vo){
        //이전 가장 최신 id를 가져옵니다.
        Integer count = notifiedMapper.prevNotifiedNum();
        if(count == null || count == 0) {
            count = 1;
        }else{
            count++;
        }
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = currentDate.format(formatter);
        String formatcount =String.format("%05d",count);
        
        if(vo.getNotiNum() == null || vo.getNotiNum().isEmpty()) {
            vo.setNotiNum(formattedDate + "cafe" + formatcount);
        }
        int upsert =  notifiedMapper.insertOrUpdateNotified(vo);
        if(upsert > 0){
            return upsert;
        }else{
            return -1;
        }
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

    public boolean deleteNotification(int id)throws Exception{
        int deleteNotification = notifiedMapper.deleteNotification(id);
        try {
            if(deleteNotification == 1){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }


}
