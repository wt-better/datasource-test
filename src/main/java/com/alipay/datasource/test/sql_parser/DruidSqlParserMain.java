package com.alipay.datasource.test.sql_parser;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;

import java.util.List;

/**
 * Druid sql parser
 *
 * @author wangte
 * Create At 2021/11/13
 * @link https://github.com/alibaba/druid.git
 */
public class DruidSqlParserMain {

    public static void main(String[] args) {
        String sql = "select a.id,a.name from user a where a.name = 'lisa' and a.id > 0 order by id desc limit 1";
        List<SQLStatement> sqlStatements = SQLUtils.toStatementList(sql, DbType.mysql);
        System.out.println(sqlStatements);
    }

}
