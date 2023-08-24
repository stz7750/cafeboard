package com.board.demo.config;

import com.board.demo.member.vo.MemberVO;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;



public class SecurityUser extends User {
    private static final long serialVersionUID = 1L;

    private MemberVO memberVO;

    public SecurityUser(MemberVO memberVO){
        super(memberVO.getId(), "{noop}"+memberVO.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_" + memberVO.getLevel().toString()));

        this.memberVO = memberVO;

    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public MemberVO getUser() {
        return memberVO;
    }

    public void setUser(MemberVO memberVO) {
        this.memberVO = memberVO;
    }
}
