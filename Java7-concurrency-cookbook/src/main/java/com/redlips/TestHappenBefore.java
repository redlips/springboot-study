package com.redlips;

/**
 * @author qiaotong
 * @create 2019-06-26 9:50
 * @description
 */
public class TestHappenBefore {
    static int var0 = 3;
    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            System.out.println("此时线程" + Thread.currentThread().getName() + "读取到的值var0 = " + var0);
            var0 = 10;
        });

        var0 = 5;

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("此时线程" + Thread.currentThread().getName() + "读取到的值var0 = " + var0);
    }
}
