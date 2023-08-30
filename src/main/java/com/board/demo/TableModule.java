package com.board.demo;

import com.board.demo.content.vo.ContentVO;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TableModule {

    //데이터를 받아 테이블로 만들어줍니다.
    public static String createTable(List<ContentVO> dataList,String tableName) {
        StringBuilder htmlBuilder = new StringBuilder();
        if (dataList == null || dataList.isEmpty()) {
            htmlBuilder.append("<tr><td colspan=\"5\" class=\"text-center\">데이터가 없습니다.</td></tr>");
        } else {
            for (ContentVO content : dataList) {
                htmlBuilder.append("<tr>");
                htmlBuilder.append("<td class=\"text-center\">").append(content.getCategory()).append("</td>");
                htmlBuilder.append("<td style=\"text-align: center;\"><a href=\"/content/boardView?id=").append(content.getId()).append("\" style=\"text-decoration: none; color: black;\">").append(content.getTitle()).append("</a></td>");
                htmlBuilder.append("<td style=\"text-align: center;\">").append(content.getAuthor()).append("</td>");
                htmlBuilder.append("<td class=\"text-center\">").append(content.getCreDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"))).append("</td>");
                if (tableName.equals("recTable")) {
                    htmlBuilder.append("<td style=\"text-align: center;\">").append(content.getRecCount()).append("</td>");
                } else if (tableName.equals("viewTable")) {
                    htmlBuilder.append("<td style=\"text-align: center;\">").append(content.getViews()).append("</td>");
                }
                htmlBuilder.append("</tr>");
            }
        }
        return htmlBuilder.toString();
    }
}

