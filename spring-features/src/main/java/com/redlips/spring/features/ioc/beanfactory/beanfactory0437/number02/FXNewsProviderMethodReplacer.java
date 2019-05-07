package com.redlips.spring.features.ioc.beanfactory.beanfactory0437.number02;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

/**
 * @author qiaotong
 * @create 2019-02-15 14:19
 * @description 演示方法替换，AOP大道，和方法注入的不同是方法替换注重方法的实现层，可以替换掉之前方法的实现逻辑
 */
public class FXNewsProviderMethodReplacer implements MethodReplacer {
    @Override
    public Object reimplement(Object obj, Method method, Object[] args) {
        System.out.printf("before executing method[ %s ], on Object [ %s ]", method.getName(), obj.getClass().getName());
        System.out.println();
        System.out.println("sorry, we will do nothing this time");

        System.out.printf("end of executing method[ %s ], on Object [ %s ]", method.getName(), obj.getClass().getName());

        return null;
    }
}
