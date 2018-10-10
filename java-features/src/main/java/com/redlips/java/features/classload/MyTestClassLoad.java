package com.redlips.java.features.classload;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * @description: 测试类加载器
 * @author: dell
 * @createTime: 2018-09-11 23:48
 * @version: 1.0
 * @copyright: qiaotong
 * @modify: dell
 */
public class MyTestClassLoad {
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            FileClassLoader fileClassLoader = new FileClassLoader("");
            System.out.println("自定义加载器的父加载器：" + fileClassLoader.getParent());
            System.out.println("系统默认加载器AppClassLoader：" + ClassLoader.getSystemClassLoader());
            System.out.println("AppClassLoader的父类加载器：" + ClassLoader.getSystemClassLoader().getParent());
            System.out.println("ExtClassLoader的父类加载器：" + ClassLoader.getSystemClassLoader().getParent().getParent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String rootDir = "F:/springboot-stduy-parent/springboot-features/target/classes";

        // 创建两个不同的自定义类加载器实例
        FileClassLoader loader1 = new FileClassLoader(rootDir);
        FileClassLoader loader2 = new FileClassLoader(rootDir);
//        // 通过findClass创建类的Class对象
//        Class<?> object1 = loader1.findClass("com.redlips.java.features.classload.ClassLoadTestBean");
//        Class<?> object2 = loader2.findClass("com.redlips.java.features.classload.ClassLoadTestBean");
//        System.out.println("object1的hashcode：" + object1.hashCode());
//        System.out.println("object2的hashcode：" + object2.hashCode());

        // ======================================================================================================

//        // 如果调用父类的loadClass方法
//        Class<?> object3 = loader1.loadClass("com.redlips.java.features.classload.ClassLoadTestBean");
//        Class<?> object4 = loader2.loadClass("com.redlips.java.features.classload.ClassLoadTestBean");
//        System.out.println("object3的hashcode：" + object3.hashCode());
//        System.out.println("object4的hashcode：" + object4.hashCode());
//        System.out.println("Class->ClassLoadTestBean：" + ClassLoadTestBean.class.hashCode());

        // ======================================================================================================

//        try {
//            Class<?> testClass = loader1.loadClass("com.redlips.java.features.classload.ClassLoadTestBean");
//            System.out.println("加载Class文件的加载器是：" + loader1.toString() + "；加载的实例结果是：" + testClass.newInstance().toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // ======================================================================================================

        // 创建自定义文件加载器
        File file = new File(rootDir);
        // file to uri
        URI uri = file.toURI();
        try {
            URL[] url = {uri.toURL()};
            FileUrlClassLoad loader = new FileUrlClassLoad(url);
            // 加载指定的文件
            Class<?> loadClass = loader.loadClass("com.redlips.springboot.features.classload.ClassLoadTestBean");
            System.out.println(loadClass.newInstance().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
