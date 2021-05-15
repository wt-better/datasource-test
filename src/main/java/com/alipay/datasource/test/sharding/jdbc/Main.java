//package com.alipay.datasource.test.sharding.jdbc;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//import javax.sql.DataSource;
//
//import com.mysql.jdbc.Driver;
//import org.apache.commons.dbcp.BasicDataSource;
//import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
//import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
//import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
//import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//
///**
// * @author yi.xia
// * @date Create At 2020/11/14
// * @link <a href=mailto:yixia.wt@alibaba-inc.com></a>
// */
//public class Main {
//
//    public static void main(String[] args) throws Exception {
//        Map<String, DataSource> dataSourceMap = buildDataSourceMap();
//
//        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
//
//        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("tbl_user",
//            "ds${0..1}.tbl_user_${0..1}");
//        tableRuleConfiguraxation.setDatabaseShardingStrategyConfig(
//            new InlineShardingStrategyConfiguration("order_id", "ds${order_id % 2}"));
//        tableRuleConfiguration.setTableShardingStrategyConfig(
//            new InlineShardingStrategyConfiguration("order_id", "tbl_user_0${order_id % 2}"));
//
//        shardingRuleConfiguration.getTableRuleConfigs().add(tableRuleConfiguration);
//
//        DataSource sharingDataSource;
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        try {
//            sharingDataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap,
//                shardingRuleConfiguration, new Properties());
//
//            connection = sharingDataSource.getConnection();
//
//            //String sql = "insert into tbl_user(user_name,order_id) values('json',5)";
//            String sql = "select * from tbl_user where user_name = 'json' limit 2 offset 0 ";
//            preparedStatement = connection.prepareStatement(sql);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString(1));
//                System.out.println(resultSet.getString(2));
//
//            }
//
//        } finally {
//            if (preparedStatement != null) {
//                preparedStatement.close();
//
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        }
//
//        System.out.println("execute end...");
//    }
//
//    private static Map<String, DataSource> buildDataSourceMap() {
//        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
//
//        String mysqlDriverClassName = Driver.class.getName();
//
//        DataSource firstDataSource = DataSourceBuilder.create()
//            .type(BasicDataSource.class)
//            .driverClassName(mysqlDriverClassName)
//            .url("jdbc:mysql://localhost:3306/database1")
//            .username("root")
//            .password("123456")
//            .build();
//
//        dataSourceMap.put("ds0", firstDataSource);
//
//        DataSource secondDataSource = DataSourceBuilder.create()
//            .type(BasicDataSource.class)
//            .driverClassName(mysqlDriverClassName)
//            .url("jdbc:mysql://localhost:3306/database2")
//            .username("root")
//            .password("123456")
//            .build();
//
//        dataSourceMap.put("ds1", secondDataSource);
//
//        return dataSourceMap;
//    }
//
//}
