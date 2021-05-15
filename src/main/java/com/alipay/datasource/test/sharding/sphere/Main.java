package com.alipay.datasource.test.sharding.sphere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;

/**
 * @author yi.xia
 * @date Create At 2021/5/14
 * @link <a href=mailto:yixia.wt@alibaba-inc.com></a>
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置第 1 个数据源
        HikariDataSource dataSource1 = new HikariDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setJdbcUrl("jdbc:mysql://localhost:3306/database1");
        dataSource1.setUsername("root");
        dataSource1.setPassword("123456");
        dataSourceMap.put("ds0", dataSource1);

        // 配置第 2 个数据源
        HikariDataSource dataSource2 = new HikariDataSource();
        dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource2.setJdbcUrl("jdbc:mysql://localhost:3306/database2");
        dataSource2.setUsername("root");
        dataSource2.setPassword("123456");
        dataSourceMap.put("ds1", dataSource2);

        // 配置 tbl_user 表规则
        ShardingTableRuleConfiguration orderTableRuleConfig = new ShardingTableRuleConfiguration("tbl_user",
            "ds${0..1}.tbl_user_0${0..1}");


        //分库分表规则配置
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        // 配置分库算法
        Properties dbShardingAlgorithmProps = new Properties();
        dbShardingAlgorithmProps.setProperty("algorithm-expression", "ds${order_id % 2}");
        shardingRuleConfig.getShardingAlgorithms().put("dbShardingAlgorithm",
            new ShardingSphereAlgorithmConfiguration("INLINE", dbShardingAlgorithmProps));

        // 配置分表算法
        Properties tableShardingAlgorithmProps = new Properties();
        tableShardingAlgorithmProps.setProperty("algorithm-expression", "tbl_user_0${order_id % 2}");
        shardingRuleConfig.getShardingAlgorithms().put("tableShardingAlgorithm",
            new ShardingSphereAlgorithmConfiguration("INLINE", tableShardingAlgorithmProps));

        // 配置分库策略
        orderTableRuleConfig.setDatabaseShardingStrategy(
            new StandardShardingStrategyConfiguration("order_id", "dbShardingAlgorithm"));

        // 配置分表策略
        orderTableRuleConfig.setTableShardingStrategy(
            new StandardShardingStrategyConfiguration("order_id", "tableShardingAlgorithm"));

        // 配置分片规则
        shardingRuleConfig.getTables().add(orderTableRuleConfig);


        DataSource sharingDataSource;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // 创建 ShardingSphereDataSource
            sharingDataSource = ShardingSphereDataSourceFactory.createDataSource(dataSourceMap,
                Collections.singleton(shardingRuleConfig), new Properties());

            connection = sharingDataSource.getConnection();

            String sql = "insert into tbl_user(user_name,order_id) values('lili',6)";
            //String sql = "select * from tbl_user where user_name = 'json' limit 2 offset 0 ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            //resultSet =
            //while (resultSet.next()) {
            //    System.out.println(resultSet.getString(1));
            //    System.out.println(resultSet.getString(2));
            //
            //}
        } finally {
            if (resultSet != null) {
                resultSet.close();

            }
            if (preparedStatement != null) {
                preparedStatement.close();

            }
            if (connection != null) {
                connection.close();
            }

        }

        System.out.println("execute end...");
    }
}
