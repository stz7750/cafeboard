package com.board.demo.content.mapper;


import com.board.demo.content.vo.ContentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface ContentMapper {
    List<ContentVO> selectContentList(@Param("start")int start, @Param("size")int size);
    int selectContentListCnt();


    ContentVO getMemberContent(@Param("id")int id , @Param("author")String author);

    int addBoardContent (ContentVO contentVO);

    ContentVO getByContentId(@Param("id")int id);

    //추천 버튼 +1
    int recCountUp(int id);

    //insert 추천멤버
    int recMemberId(@Param("author") String author,@Param("id")int id);
    //회원의 특정 게시물 추천을 여부
    boolean cntRecMemberId(@Param("author")String author,@Param("id") int id);
    //게시물 수정
    int updateContent(@Param("id") int id, @Param("author") String author, @Param("content") ContentVO contentVO);

    List<ContentVO> searchContent(@Param("searchType")String searchType,@Param("searchValue")String searchValue,
            @Param("start")int start,@Param("size")int size);
    int searchContentCnt(@Param("searchType")String searchType, @Param("searchValue")String searchValue,
                         @Param("start")int start,@Param("size")int size);

    int viewUp(@Param("id")int id);

    List<ContentVO> getTodayMostRec();

    List<ContentVO> getTodayMostView();

    List<ContentVO> getWeekMostRec();

    List<ContentVO> getWeekMostView();

    List<ContentVO> getCategoryByContent(@Param("category")String category);

    List<ContentVO> getRankingRec();
}
