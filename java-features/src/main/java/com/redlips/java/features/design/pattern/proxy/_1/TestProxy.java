package com.redlips.java.features.design.pattern.proxy._1;

import com.redlips.java.features.design.pattern.proxy.UserDao;

/**
 * @author qiaotong
 * @create 2019-06-13 9:16
 * @description 静态代理，目标对象和代理对象都要实现同一个接口
 */
public class TestProxy {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        UserDaoProxy userDaoProxy = new UserDaoProxy(userDao);
        userDaoProxy.save();
    }
}
