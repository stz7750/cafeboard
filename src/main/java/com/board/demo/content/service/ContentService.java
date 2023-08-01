package com.board.demo.content.service;


import com.board.demo.content.mapper.ContentMapper;
import com.board.demo.content.vo.ContentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    @Autowired
    ContentMapper contentMapper;

    public List<ContentVO> selectContentList(){
        return contentMapper.selectContentList();
    }
}
