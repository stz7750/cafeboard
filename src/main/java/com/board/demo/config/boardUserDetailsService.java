package com.board.demo.config;

import com.board.demo.serivce.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.board.demo.vo.MemberVO;

@Service
public class boardUserDetailsService implements UserDetailsService {


    @Autowired
    MemberService MemberService;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        MemberVO member = MemberService.findByUserinfo(memberId);
        if (member == null) {
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + memberId);
        }
        return new SecurityUser(member);
    }
}
