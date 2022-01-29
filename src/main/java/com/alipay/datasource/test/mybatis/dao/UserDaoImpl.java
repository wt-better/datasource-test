package com.alipay.datasource.test.mybatis.dao;

import com.alipay.datasource.test.mybatis.domain.User;
import org.springframework.stereotype.Repository;

/**
 * @author yi.xia
 * @date Create At 2021/7/14
 * @link <a href=mailto:yixia.wt@alibaba-inc.com></a>
 */
@Repository
public class UserDaoImpl extends AbstractDao implements UserDao {

    @Override
    public void insert(User user) {
        getSqlSession().insert("insert", user);
    }
}
