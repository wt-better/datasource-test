package com.alipay.datasource.test.jdbc.batch;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangte
 * Create At 2021/7/3
 */
public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static final DataSource DATA_SOURCE = new DriverManagerDataSource(URL, USER, PASSWORD);
    private static final JdbcTemplate JDBC_TEMPLATE = new JdbcTemplate(DATA_SOURCE);

    public static void main(String[] args) {
        //queryOne();
        updateBatch();
    }

    private static void updateBatch() {
        String sql = "insert into user_tbl(user_name) values(?)";
        List<Object[]> batchArgs = new ArrayList<>();
        batchArgs.add(new Object[]{"go"});
        batchArgs.add(new Object[]{"java"});
        int[] counts = JDBC_TEMPLATE.batchUpdate(sql, batchArgs);
        Arrays.stream(counts).forEach(System.out::println);
    }

    private static void queryOne() {
        String sql = "select * FROM user_tbl WHERE id = 1";
        User user = JDBC_TEMPLATE.query(sql, rs -> {
            rs.next();
            Long id = rs.getLong(1);
            String userName = rs.getString(2);
            return new User(id, userName);
        });

        System.out.println(user);
    }
}
