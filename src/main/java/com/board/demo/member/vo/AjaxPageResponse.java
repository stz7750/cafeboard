package com.board.demo.member.vo;


import lombok.Data;
import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class AjaxPageResponse<T> {
    private int code;
    private String message;
    private Page<T> items;

    public AjaxPageResponse() {

    }

    public AjaxPageResponse(int code, String message, Page<T> items) {
        this.code = code;
        this.message = message;
        this.items = items;
    }
}