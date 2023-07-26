package com.board.demo.vo;

import lombok.Data;

@Data
public class MemberVO {
    private String id;
    private String name;
    private String password;
    private String level;
    private String address;
    private String email;
}
