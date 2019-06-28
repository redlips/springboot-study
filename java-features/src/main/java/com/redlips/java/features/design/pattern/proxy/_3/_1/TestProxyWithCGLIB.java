package com.redlips.java.features.design.pattern.proxy._3._1;

import com.redlips.java.features.design.pattern.proxy.UserDao;

/**
 * @author qiaotong
 * @create 2019-06-13 14:26
 * @description
 */
public class TestProxyWithCGLIB {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        System.out.println(userDao.getClass());

        UserDao proxyInstance = (UserDao) new ProxyFactoryWithCGLIB(userDao).getProxyInstance();
        System.out.println(proxyInstance.getClass());

        proxyInstance.save();
    }
}
