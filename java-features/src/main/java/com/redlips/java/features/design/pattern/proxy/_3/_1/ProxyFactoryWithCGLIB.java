package com.redlips.java.features.design.pattern.proxy._3._1;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author qiaotong
 * @create 2019-06-13 14:19
 * @description
 */
public class ProxyFactoryWithCGLIB implements MethodInterceptor {
    // 目标对象
    private Object targetObject;

    public ProxyFactoryWithCGLIB(Object targetObject) {
        this.targetObject = targetObject;
    }

    /**
     * 为目标对象创建代理对象
     * @return
     */
    public Object getProxyInstance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetObject.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("开启事务");
        method.invoke(targetObject, objects);
        System.out.println("关闭事务");
        return null;
    }
}
