package com.alipay.datasource.test.jdbc.trx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author yi.xia
 * @date Create At 2022/1/29
 * @link <a href=mailto:yixia.wt@alibaba-inc.com></a>
 */
public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_test";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) throws Exception {
        test1();
        //test2();
    }

    private static void test1() throws Exception {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        connection.setAutoCommit(false);
        Statement statement1 = null;
        Statement statement2 = null;
        try {
            statement1 = connection.createStatement();

            statement1.execute("insert into test (id, name) values (19,'ning');");

            statement2 = connection.createStatement();
            //this will primary key dup exception
            statement2.execute("insert into test (id, name) values (1,'ruler');");

            connection.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            //判断没有执行rollback的场景下，最终生效的是几个
            //connection.rollback();
        } finally {
            assert statement1 != null;
            statement1.close();
            assert statement2 != null;
            statement2.close();
        }

        Statement statement3 = connection.createStatement();
        statement3.execute("begin ;");
        statement3.execute("insert into test (id, name) values (21,'tian');");
        statement3.execute("commit ;");

        statement3.close();

        connection.close();
    }

    private static void test2() throws Exception {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        connection.setAutoCommit(false);
        Statement statement1 = null;
        Statement statement2 = null;
        try {
            statement1 = connection.createStatement();

            statement1.execute("insert into test (id, name) values (19,'ning');");

            statement2 = connection.createStatement();
            //this will primary key dup exception
            statement2.execute("insert into test (id, name) values (1,'ruler');");

            connection.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            //判断没有执行rollback的场景下，最终生效的是几个
            //connection.rollback();
        } finally {
            assert statement1 != null;
            statement1.close();
            assert statement2 != null;
            statement2.close();
        }

        Statement statement3 = connection.createStatement();
        statement3.execute("insert into test (id, name) values (21,'tian');");
        connection.commit();

        statement3.close();

        connection.close();
    }
}
