package com.alipay.datasource.test.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alipay.datasource.test.mybatis.domain.User;

/**
 * @author yi.xia
 * @date Create At 2021/2/9
 * @link <a href=mailto:yixia.wt@alibaba-inc.com></a>
 */
public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_test?allowMultiQueries=true&rewriteBatchedStatements=true";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";


    private static void insertBatch() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        PreparedStatement preparedStatement = connection.prepareStatement("insert into tbl_user(id,user_name) values (?,?);");

        List<User> list = new ArrayList<>();
        list.add(new User(24L,"lisa"));
        list.add(new User(25L,"yel"));

        for (User user : list) {
            preparedStatement.setInt(1, user.getId().intValue());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();

        preparedStatement.close();
        connection.close();
    }

    public static void main(String[] args) throws SQLException {
        insertBatch();
        //Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        //
        //PreparedStatement preparedStatement = connection.prepareStatement("select * FROM test WHERE id in (?,?)");
        //ResultSet resultSet = preparedStatement.executeQuery();
        //
        //while (resultSet.next()) {
        //    System.out.println(resultSet.getInt(1) + "-" + resultSet.getString(2));
        //}
        //
        //resultSet.close();
        //preparedStatement.close();
        //connection.close();

        //PreparedStatement preparedStatement = connection.prepareStatement(
        //    "insert into test(id, name) values (null,'jack'),(null,'jack2')", Statement.RETURN_GENERATED_KEYS);
        //
        //preparedStatement.executeUpdate();
        //
        //ResultSet resultSet = preparedStatement.getGeneratedKeys();
        //while (resultSet.next()) {
        //    System.out.println(resultSet.getInt(1));
        //}
        //
        //resultSet.close();
        //preparedStatement.close();
        //connection.close();
    }
}
