package com.board.demo.member.serivce;


import com.board.demo.content.vo.ContentVO;
import com.board.demo.member.mapper.MemberMapper;
import com.board.demo.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class MemberService {

    @Autowired
    MemberMapper mapper;

    public List<MemberVO> getMember(){
        return mapper.selectMember();
    }

    @Transactional
    public int addMember(MemberVO vo){
        //회원의 레벨의 member로 기본적으로 설정합니다.
        vo.setLevel("member");
        return mapper.insertMember(vo);
    }

    public MemberVO findByUserinfo(String memberId){

        MemberVO member =  mapper.findByUserinfo(memberId);
        String[] addressArray = member.getAddress().split(",");
        if (addressArray.length >= 2) {
            member.setAddr1(addressArray[0].trim()); // 앞부분을 addr1에 저장
            member.setAddr2(addressArray[1].trim()); // 뒷부분을 addr2에 저장
        } else {
            member.setAddr1(member.getAddress().trim()); // 쉼표가 없는 경우, 전체 주소를 addr1에 저장
            member.setAddr2(""); // addr2는 비워둠
        }

        return member;
    }

    public int memberContentCnt(String author){
        return mapper.memberContentListCnt(author);
    }

    public int memberRecCount(String author){
        return mapper.memberRecCount(author);
    }
    @Transactional
    public int memberInfoModify(MemberVO vo){
       return mapper.memberInfoModify(vo);
    }

    public List<ContentVO> getMemberContent(String author){
        return mapper.getMemberContent(author);
    }
    public Page<ContentVO> getMemberContentPaging(int page,int size,String author){

        final int start = (page -1)* size;
        final int count = mapper.memberContentListCnt(author);

        List<ContentVO> list = mapper.memberAllContent(start,size,author);
        Page<ContentVO> result = new PageImpl<ContentVO>(list, PageRequest.of(page -1,size),count);
        return result;
    }
    @Transactional
    public String deleteMemberContent(List<Integer> ids, String author) {
        int successCount = 0;
        for(int id : ids) {
            int delete = mapper.deleteMemberContent(id, author);
            if(delete == 1){
                successCount++;
            }
        }
        if(successCount == ids.size()){
            return "success";
        }else{
            return "fail";
        }
    }

    public Integer todayMemberContent(String author) throws Exception{
        try {
            int tCntContent = mapper.todayMemberContent(author);
            return tCntContent;
        }catch (Exception e){
            return -1;
        }
    }

    public Integer monthMemberContent(String author) throws Exception{
        try{
            int mCntContent = mapper.monthMemberContent(author);
            return mCntContent;
        } catch (Exception e){
            return -1;
        }
    }

    public Integer todayMemberRec(String author)throws Exception{
        try{
            int tCntRec = mapper.todayMemberRec(author);
            return tCntRec;
        } catch (Exception e){
            return -1;
        }
    }

    public Integer monthMemberRec(String author) throws Exception{
        try{
            int mCntRec = mapper.monthMemberRec(author);
            return mCntRec;
        } catch (Exception e){
            return -1;
        }
    }

    public void updateLastLogin(MemberVO vo){
        mapper.updateLastLogin(vo);
    }
}
