package com.redlips.java.features.design.pattern.proxy._3._2;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author qiaotong
 * @create 2019-06-13 14:48
 * @description
 */
public class TestCglib {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallback(new TargetInterceptor());
        TargetObject targetObject = (TargetObject) enhancer.create();

        System.out.println(targetObject);
        System.out.println(targetObject.method1("xxx"));
    }
}
