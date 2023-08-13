package com.board.demo.content.service;


import com.board.demo.content.mapper.ContentMapper;
import com.board.demo.content.vo.ContentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContentService {

    @Autowired
    ContentMapper contentMapper;

    private final Object viewUpLock = new Object();


    public Page<ContentVO> getContentPaging(int page, int size){
        final int start = (page -1)* size;
        final int count = contentMapper.selectContentListCnt();

        List<ContentVO> list = contentMapper.selectContentList(start,size);
        Page<ContentVO> result = new PageImpl<ContentVO>(list,PageRequest.of(page -1,size),count);
        return result;
    }

    public Page<ContentVO> getSearchPaging(String searchType,String searchValue,int page,int size){
        final int start = (page -1)*size;
        final int count = contentMapper.searchContentCnt(searchType,searchValue,start,size);

        List<ContentVO> list = contentMapper.searchContent(searchType,searchValue,start,size);
        Page<ContentVO> result = new PageImpl<ContentVO>(list,PageRequest.of(page -1,size),count);
        return result;
    }



    public ContentVO getMemberContent(int id, String author){
        return contentMapper.getMemberContent(id,author);
    }

    public String addBoardContent(ContentVO contentVO) {
        int result = contentMapper.addBoardContent(contentVO);
        if (result > 0) {
            return "success"; // 성공적으로 저장되었을 때
        } else {
            return "failure"; // 저장 실패 시
        }
    }
    @Transactional
    public ContentVO getByContentId(int id)throws DataAccessException {
            //조회수 증가
            contentMapper.viewUp(id);
            return contentMapper.getByContentId(id);
    }
    public String addRecommendation(String author, int id){
        boolean cntRecMemberId = contentMapper.cntRecMemberId(author,id);
        if(!cntRecMemberId){
            contentMapper.recCountUp(id); //추천수 증가
            contentMapper.recMemberId(author,id); // 추천 한 멤버 insert
            return "success";
        }else{
            return "alreadyRec";
        }
    }

    public boolean updateContent(int id, String author, ContentVO contentVO) {
        int result = contentMapper.updateContent(id, author, contentVO);
        return result > 0;
    }
}
