package com.redlips;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qiaotong
 * @create 2019-01-17 13:37
 * @description
 */
public class Test {
//    public volatile int inc = 0;
    public AtomicInteger inc = new AtomicInteger();

    public void increase() {
        inc.getAndIncrement();
    }

    public static void main(String[] args) {
//        final Test test = new Test();
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                for (int j = 0; j < 1000; j++)
//                    test.increase();
//            }).start();
//        }
//
//        while (Thread.activeCount() > 1) {
//            Thread.yield();
//        }
//        System.out.println(test.inc);
        int i = test1(150);
        System.out.println(i);
        Integer i1 = 1;
        Integer i2 = 1;
        System.out.println(i1.hashCode());
        System.out.println(i2.hashCode());
        System.out.println(i1.equals(i2));
    }

    static final int MAXIMUM_CAPACITY = 1 << 30;
    public static int test1(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
