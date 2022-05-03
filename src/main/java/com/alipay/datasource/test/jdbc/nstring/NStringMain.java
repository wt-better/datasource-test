package com.alipay.datasource.test.jdbc.nstring;

import java.sql.*;

/**
 * @author wangte
 * Create At 2022/5/2
 */
@SuppressWarnings({"DuplicatedCode", "AlibabaClassNamingShouldBeCamel"})
public class NStringMain {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) throws SQLException {
        //testSetNString();
        testGetNString();
    }

    private static void testSetNString() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into tbl_order(order_name,order_amount) values (?,?)");
        preparedStatement.setNString(1, "order_name_1");
        preparedStatement.setNString(2, "100");

        preparedStatement.execute();

        preparedStatement.close();
        connection.close();
    }

    private static void testGetNString() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM tbl_order WHERE id =2");
        while (resultSet.next()) {
            String id = resultSet.getNString(1);
            System.out.println(id);
            String orderName = resultSet.getNString(2);
            System.out.println(orderName);
            String orderAmount = resultSet.getNString(3);
            System.out.println(orderAmount);
        }

        statement.close();
        connection.close();
    }
}
