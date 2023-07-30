package com.board.demo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class dataBaseConfig {

    private JdbcTemplate jdbcTemplate;

    public dataBaseConfig(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void queryAndLog(String sql, Map<String, ?> paramMap) {
        System.out.println("Executing query: " + sql);
        System.out.println("Parameters: " + paramMap);
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, paramMap);
        System.out.println("Result:");
        for (Map<String, Object> row : resultList) {
            System.out.println(row);
        }
    }

    public static void main(String[] args) {
        // 데이터베이스 연결 설정 (DataSource)
        DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/board")
                .username("stz7750")
                .password("846223")
                .driverClassName("org.postgresql.Driver")
                .build();

        dataBaseConfig dataBaseConfig = new dataBaseConfig(dataSource);

        // 쿼리 실행 및 로깅
        String sql = "SELECT * FROM 테이블명 WHERE column1 = :param1 AND column2 = :param2";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("param1", "value1")
                .addValue("param2", "value2");

        // 쿼리 실행 및 결과 콘솔에 출력
        dataBaseConfig.queryAndLog(sql, params.getValues());
    }

}
