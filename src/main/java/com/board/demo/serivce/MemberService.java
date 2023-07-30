package com.board.demo.serivce;


import com.board.demo.mapper.MemberMapper;
import com.board.demo.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MemberService {
    @Autowired
    MemberMapper mapper;

    public List<MemberVO> getMember(){
        return mapper.selectMember();
    }

    public int addMember(MemberVO vo){
        //회원의 레벨의 member로 기본적으로 설정합니다.
        vo.setLevel("member");
        return mapper.insertMember(vo);
    }

    public MemberVO findByUserinfo(String memberId){
        return mapper.findByUserinfo(memberId);
    }
}
