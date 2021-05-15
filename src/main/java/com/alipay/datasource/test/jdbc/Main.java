package com.alipay.datasource.test.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yi.xia
 * @date Create At 2021/2/9
 * @link <a href=mailto:yixia.wt@alibaba-inc.com></a>
 */
public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_test";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        PreparedStatement preparedStatement = connection.prepareStatement("select * FROM test WHERE id = 1");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1) + "-" + resultSet.getString(2));
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
