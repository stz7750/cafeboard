package com.board.demo.member.vo;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class MemberVO {
    private String id;
    private String name;
    private String password;
    private String level;
    private String address;
    private String addr1;
    private String addr2;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLogin;
}
