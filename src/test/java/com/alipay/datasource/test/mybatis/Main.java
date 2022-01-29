package com.alipay.datasource.test.mybatis;

import com.alipay.datasource.test.mybatis.dao.UserDao;
import com.alipay.datasource.test.mybatis.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yi.xia
 * @date Create At 2021/7/14
 * @link <a href=mailto:yixia.wt@alibaba-inc.com></a>
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class Main {

    @Autowired
    private UserDao userDao;

    @Test
    public void testInsert() {
        User user = new User();
        user.setUserName("coco");
        userDao.insert(user);
    }
}
