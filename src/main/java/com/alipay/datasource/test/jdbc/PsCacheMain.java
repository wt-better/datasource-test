package com.alipay.datasource.test.jdbc;

import java.sql.*;

/**
 * https://www.infoq.cn/article/2017/03/Analysis-errors-MySQL-JDBC
 * <p>
 * https://blog.csdn.net/gao_zhennan/article/details/91442687
 *
 * @author wangte
 * Create At 2021/7/10
 */
public class PsCacheMain {

    /**
     * useServerPrepStmts=true&cachePrepStmts=true
     */
    private static final String URL = "jdbc:mysql://localhost:3306/test?&cachePrepStmts=true";

    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) throws SQLException, InterruptedException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM user_tbl WHERE id = ?");
        preparedStatement1.setLong(1,1);
        ResultSet rs1 = preparedStatement1.executeQuery();
        rs1.close();
        preparedStatement1.close();


        PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * FROM user_tbl WHERE id = ?");
        preparedStatement2.setLong(1,2);
        ResultSet rs2 = preparedStatement2.executeQuery();
        rs2.close();
        preparedStatement2.close();

        connection.close();
    }
}
