package com.board.demo.config;

import com.board.demo.member.serivce.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.board.demo.member.vo.MemberVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardUserDetailsService implements UserDetailsService {


    @Autowired
    MemberService MemberService;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        MemberVO member = MemberService.findByUserinfo(memberId);
        if (member == null) {
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + memberId);
        }
        MemberVO vo = new MemberVO();
        vo.setLastLogin(LocalDateTime.now());
        vo.setId(memberId);
        MemberService.updateLastLogin(vo);
        return new SecurityUser(member);
    }
}
