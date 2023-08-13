package com.board.demo.member.mapper;


import com.board.demo.content.vo.ContentVO;
import com.board.demo.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface MemberMapper {

    List<MemberVO> selectMember();

    int insertMember(MemberVO vo);

    MemberVO findByUserinfo(@Param("memberId")String memberId);

    int memberInfoModify(MemberVO vo);

    List<ContentVO> getMemberContent(@Param("author")String author);
    List<ContentVO> memberAllContent(@Param("start")int start,@Param("size")int size,@Param("author")String author);

    int memberContentListCnt(@Param("author")String author);

    int memberRecCount(@Param("author")String author);

    int deleteMemberContent(@Param("id")int id,@Param("author")String author);

    int todayMemberContent(@Param("author")String author);

    int monthMemberContent(@Param("author")String author);

    int todayMemberRec(@Param("author")String author);

    int monthMemberRec(@Param("author")String author);
}
