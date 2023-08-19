package com.board.demo.admin.vo;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotifiedVO {
    private int id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate;
    private String showYn;
    private String notiImg;
    private String notiNum;
}
