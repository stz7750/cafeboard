package com.board.demo.content.mapper;


import com.board.demo.content.vo.ContentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContentMapper {
    List<ContentVO> selectContentList();
}
