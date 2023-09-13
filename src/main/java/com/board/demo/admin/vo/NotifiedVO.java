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
    private String notiImgName;
    private String notiNum;
    private String modifyId;

    /* 23.09.12 추가*/
    private String category;
    private String starttime;
    private String endtime;
}
