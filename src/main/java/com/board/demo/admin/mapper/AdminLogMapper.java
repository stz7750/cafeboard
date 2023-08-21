package com.board.demo.admin.mapper;


import com.board.demo.admin.vo.AdminLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminLogMapper {

    List<AdminLogVO> selectAdminLog(@Param("start")int start, @Param("size")int size);
    int cntAdminLog();
    int insertAdminLog(AdminLogVO vo);
}
