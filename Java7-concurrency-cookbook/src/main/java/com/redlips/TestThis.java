package com.redlips;

/**
 * @author qiaotong
 * @create 2019-06-26 16:22
 * @description
 */
public class TestThis {
    public static Object object;
    public static void main(String[] args) {

    }

    static class ClassA {
        private int age;
        private String name;

        public ClassA(int age, String name) {
            this.age = age;
            this.name = name;
            object = this;
        }
    }
}
