package com.alipay.datasource.test.mybatis.domain;

/**
 * @author yi.xia
 * @date Create At 2021/7/14
 * @link <a href=mailto:yixia.wt@alibaba-inc.com></a>
 */
public class User {

    private Long id;
    private String userName;

    public User() {
    }

    public User(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
