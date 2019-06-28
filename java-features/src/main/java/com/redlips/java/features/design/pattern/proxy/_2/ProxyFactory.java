package com.redlips.java.features.design.pattern.proxy._2;

import java.lang.reflect.Proxy;

/**
 * @author qiaotong
 * @create 2019-06-13 9:34
 * @description
 */
public class ProxyFactory {
    // 维护一个目标对象
    private Object targetObject;

    public ProxyFactory(Object targetObject) {
        this.targetObject = targetObject;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    System.out.println("开启事务");
                    method.invoke(targetObject, args);
                    System.out.println("提交事务");
                    return null;
                });
    }
}
