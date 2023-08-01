package com.board.demo.content.vo;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContentVO {
    private int id;
    private String category;
    private String title;
    private String author;
    private LocalDateTime creDate;
    private int views;
    private LocalDateTime modifiedDate;
}
