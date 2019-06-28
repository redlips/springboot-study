package com.redlips.java.features.design.pattern.proxy._2;

import com.redlips.java.features.design.pattern.proxy.IUserDao;
import com.redlips.java.features.design.pattern.proxy.UserDao;

/**
 * @author qiaotong
 * @create 2019-06-13 9:54
 * @description 动态代理对象是在运行时动态生成的，而是在运行动态生成类字节码，并加载到JVM中
 * 动态代理对象不需要实现接口，但是目标对象必须要实现接口
 */
public class TestDynamicProxy {
    public static void main(String[] args) {
        IUserDao userDao = new UserDao();
        System.out.println("目标对象 = " + userDao);

        IUserDao proxyInstance = (IUserDao) new ProxyFactory(userDao).getProxyInstance();
        System.out.println("代理对象 = " + proxyInstance.getClass());
        proxyInstance.save();
    }
}
