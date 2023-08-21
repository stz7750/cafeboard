package com.board.demo.admin.vo;


import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class AdminLogVO {
    private String id;
    private String description;
    private LocalDateTime recordDate;
}
