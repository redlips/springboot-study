package com.redlips.springboot.features;

/**
 * @description: 测试类加载器
 * @author: dell
 * @createTime: 2018-09-11 23:48
 * @version: 1.0
 * @copyright: qiaotong
 * @modify: dell
 */
public class MyTestClassLoad {
    public static void main(String[] args) {
        try {
            System.out.println(ClassLoader.getSystemClassLoader());
            System.out.println(ClassLoader.getSystemClassLoader().getParent());
            System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
