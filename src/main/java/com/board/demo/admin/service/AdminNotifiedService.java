package com.board.demo.admin.service;


import com.board.demo.admin.mapper.AdminNotifiedMapper;
import com.board.demo.admin.vo.NotifiedVO;
import com.board.demo.global.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdminNotifiedService {

    @Autowired
    AdminNotifiedMapper notifiedMapper;

    @Autowired
    private FileStorageService fileStorageService;

    public int upsertNotified(NotifiedVO vo, MultipartFile notiImg){

        String fileName = null;
        try {
            if (notiImg != null && !notiImg.isEmpty()) {
                fileName = fileStorageService.storeFile(notiImg);
                vo.setNotiImgName(fileName);
            }
        }catch (IOException I){
            System.err.println("파일 저장 실패");
        }
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
            if(vo.getStarttime() != null && vo.getEndtime() != null){
                vo.setCategory("팝업");
            }else{
                vo.setCategory("공지");
            }
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

    public Page<NotifiedVO> getEventPopupPaging(int page, int size){
        final int start = (page -1)*size;
        final int count = notifiedMapper.cntEvent();

        List<NotifiedVO> list = notifiedMapper.selectEvent(start,size);
        Page<NotifiedVO> result = new PageImpl<NotifiedVO>(list, PageRequest.of(page -1, size),count);
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
