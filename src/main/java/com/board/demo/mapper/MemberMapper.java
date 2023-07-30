package com.board.demo.mapper;


import com.board.demo.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface MemberMapper {

    List<MemberVO> selectMember();

    int insertMember(MemberVO vo);

    MemberVO findByUserinfo(@Param("memberId")String memberId);
}
