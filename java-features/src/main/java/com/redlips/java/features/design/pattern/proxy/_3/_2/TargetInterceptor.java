package com.redlips.java.features.design.pattern.proxy._3._2;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author qiaotong
 * @create 2019-06-13 14:33
 * @description 拦截器，在调用目标方法时，CGLIB会回调MethodInterceptor接口拦截，类似JDK代理的InvocationHandler接口
 */
public class TargetInterceptor implements MethodInterceptor {
    /**
     * 重写方法拦截在方法前和方法后加入业务
     * @param obj 目标对象
     * @param method 目标方法
     * @param args 方法参数
     * @param proxy CGLib为生成的代理类对方法的代理引用
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("调用前");
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("调用后"+result);
        return result;
    }
}
