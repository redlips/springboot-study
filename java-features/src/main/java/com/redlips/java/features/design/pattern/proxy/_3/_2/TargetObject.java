package com.redlips.java.features.design.pattern.proxy._3._2;

/**
 * @author qiaotong
 * @create 2019-06-13 14:31
 * @description 目标类
 */
public class TargetObject {
    public String method1(String paramName) {
        return paramName;
    }

    public int method2(int count) {
        return count;
    }

    public int method3(int count) {
        return count;
    }

    @Override
    public String toString() {
        return "TargetObject []"+ this.getClass();
    }

}
