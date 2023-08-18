package com.board.demo.admin.mapper;


import com.board.demo.admin.vo.NotifiedVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotifiedMapper {

    int cntNotified(@Param("start")int start, @Param("size")int size);
    List<NotifiedVO> selectNotified(@Param("start")int start,@Param("size")int size);
    int insertOrUpdateNotified(NotifiedVO vo);

    void updateNotifiedShowYn(@Param("id") int id, @Param("showYn") String showYn);
}