package com.alipay.datasource.test.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * https://www.infoq.cn/article/2017/03/Analysis-errors-MySQL-JDBC
 *
 * @author yi.xia
 * @date Create At 2021/2/9
 * @link <a href=mailto:yixia.wt@alibaba-inc.com></a>
 */
public class AutoReconnectMain {

    private static final String URL = "jdbc:mysql://localhost:3306/test?autoReconnect=true";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) throws SQLException, InterruptedException {
        Connection reConnectableConn = DriverManager.getConnection(URL, USER, PASSWORD);

        ResultSet rs = reConnectableConn.createStatement().executeQuery("SELECT 1");

        assertTrue(rs.next());
        assertEquals(1, rs.getInt(1));

        //restart mysql

        rs = reConnectableConn.createStatement().executeQuery("SELECT 1");
        assertTrue(rs.next());
        assertEquals(1, rs.getInt(1));
    }
}
