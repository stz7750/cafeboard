package com.board.demo.config;

import com.board.demo.vo.MemberVO;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;



public class SecurityUser extends User {
    private static final long serialVersionUID = 1L;

    private com.board.demo.vo.MemberVO memberVO;

    public SecurityUser(com.board.demo.vo.MemberVO memberVO){
        super(memberVO.getId(), "{noop}"+memberVO.getPassword(),
                AuthorityUtils.createAuthorityList(memberVO.getLevel().toString()));

        this.memberVO = memberVO;

    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public com.board.demo.vo.MemberVO getUser() {
        return memberVO;
    }

    public void setUser(com.board.demo.vo.MemberVO memberVO) {
        this.memberVO = memberVO;
    }
}
