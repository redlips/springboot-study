package com.redlips.springboot.features.classload;

/**
 * @description: 进一步测试ClassLoad
 * @author: dell
 * @createTime: 2018-09-12 22:53
 * @version: 1.0
 * @copyright: qiaotong
 * @modify: dell
 */
public class ClassLoadTest {
    public static void main(String[] args) {
        try {
            //查看当前系统类路径中包含的路径条目，此时会显示当前工程目录的class目录
            System.out.println("打印结果:" + System.getProperty("java.class.path"));
            //调用加载当前类的类加载器（这里即为系统类加载器）加载ClassLoadTestBean
            Class typeLoaded = Class.forName("com.redlips.springboot.features.classload.ClassLoadTestBean");
            //查看被加载的TestBean类型是被那个类加载器加载的ClassLoadTestBean
            System.out.println(typeLoaded.getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
