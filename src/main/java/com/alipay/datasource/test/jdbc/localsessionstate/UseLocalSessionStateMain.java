package com.alipay.datasource.test.jdbc.localsessionstate;

import java.sql.*;

/**
 * why add useLocalSessionState param
 * https://blog.csdn.net/wt_better/article/details/108444773
 *
 * @author wangte
 * Create At 2022/5/3
 */
@SuppressWarnings({"DuplicatedCode", "AlibabaCommentsMustBeJavadocFormat"})
public class UseLocalSessionStateMain {

    //?useLocalSessionState=true
    private static final String URL = "jdbc:mysql://localhost:3306/test?useLocalSessionState=true";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    //2022-05-03T02:10:52.450968Z	    6 Connect	root@localhost on test using TCP/IP
    //2022-05-03T02:10:52.459099Z	    6 Query	/* mysql-connector-java-8.0.22 (Revision: d64b664fa93e81296a377de031b8123a67e6def2) */SELECT  @@session.auto_increment_increment AS auto_increment_increment, @@character_set_client AS character_set_client, @@character_set_connection AS character_set_connection, @@character_set_results AS character_set_results, @@character_set_server AS character_set_server, @@collation_server AS collation_server, @@collation_connection AS collation_connection, @@init_connect AS init_connect, @@interactive_timeout AS interactive_timeout, @@license AS license, @@lower_case_table_names AS lower_case_table_names, @@max_allowed_packet AS max_allowed_packet, @@net_write_timeout AS net_write_timeout, @@performance_schema AS performance_schema, @@query_cache_size AS query_cache_size, @@query_cache_type AS query_cache_type, @@sql_mode AS sql_mode, @@system_time_zone AS system_time_zone, @@time_zone AS time_zone, @@tx_isolation AS transaction_isolation, @@wait_timeout AS wait_timeout
    //2022-05-03T02:10:52.516173Z	    6 Query	SET NAMES latin1
    //2022-05-03T02:10:52.516497Z	    6 Query	SET character_set_results = NULL
    //2022-05-03T02:10:52.517623Z	    6 Query	SET autocommit=1
    //2022-05-03T02:11:00.492262Z	    6 Query	SET autocommit=0
    //2022-05-03T02:11:01.604190Z	    6 Query	SELECT @@session.tx_read_only
    //2022-05-03T02:11:01.608689Z	    6 Query	INSERT into tbl_order(order_name,order_amount) values ('trx3',10)
    //2022-05-03T02:11:02.533931Z	    6 Query	SELECT @@session.tx_read_only
    //2022-05-03T02:11:02.535970Z	    6 Query	INSERT into tbl_order(order_name,order_amount) values ('trx4',20)
    //2022-05-03T02:11:02.964862Z	    6 Query	commit
    //2022-05-03T02:11:05.622649Z	    6 Query	rollback
    //2022-05-03T02:11:05.646473Z	    6 Quit
    /** 添加useLocalSessionState=true之后server端的日志，明显看到是少了SELECT @@session.tx_read_only
     * 同时少了SET autocommit=1，这里具体参考：com.mysql.cj.jdbc.ConnectionImpl#setAutoCommit(boolean) **/
    //2022-05-03T02:15:04.792072Z	    7 Connect	root@localhost on test using TCP/IP
    //2022-05-03T02:15:04.799076Z	    7 Query	/* mysql-connector-java-8.0.22 (Revision: d64b664fa93e81296a377de031b8123a67e6def2) */SELECT  @@session.auto_increment_increment AS auto_increment_increment, @@character_set_client AS character_set_client, @@character_set_connection AS character_set_connection, @@character_set_results AS character_set_results, @@character_set_server AS character_set_server, @@collation_server AS collation_server, @@collation_connection AS collation_connection, @@init_connect AS init_connect, @@interactive_timeout AS interactive_timeout, @@license AS license, @@lower_case_table_names AS lower_case_table_names, @@max_allowed_packet AS max_allowed_packet, @@net_write_timeout AS net_write_timeout, @@performance_schema AS performance_schema, @@query_cache_size AS query_cache_size, @@query_cache_type AS query_cache_type, @@sql_mode AS sql_mode, @@system_time_zone AS system_time_zone, @@time_zone AS time_zone, @@tx_isolation AS transaction_isolation, @@wait_timeout AS wait_timeout
    //2022-05-03T02:15:04.852482Z	    7 Query	SET NAMES latin1
    //2022-05-03T02:15:04.852855Z	    7 Query	SET character_set_results = NULL
    //2022-05-03T02:15:05.467597Z	    7 Query	SET autocommit=0
    //2022-05-03T02:15:06.813041Z	    7 Query	INSERT into tbl_order(order_name,order_amount) values ('trx3',10)
    //2022-05-03T02:15:15.256692Z	    7 Query	INSERT into tbl_order(order_name,order_amount) values ('trx4',20)
    //2022-05-03T02:15:15.257380Z	    7 Query	commit
    //2022-05-03T02:15:15.259100Z	    7 Query	rollback
    //2022-05-03T02:15:15.284600Z	    7 Quit
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        Statement statement1 = null;
        Statement statement2 = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);

            statement1 = connection.createStatement();
            statement1.execute("INSERT into tbl_order(order_name,order_amount) values ('trx3',10)");

            statement2 = connection.createStatement();
            statement2.execute("INSERT into tbl_order(order_name,order_amount) values ('trx4',20)");

            connection.commit();
        } catch (Exception exception) {
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (statement1 != null) {
                statement1.close();
            }
            if (statement2 != null) {
                statement2.close();
            }
            if (connection != null) {
                connection.close();
            }
        }


    }

}
