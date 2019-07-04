package com.redlips.multithreading.test14;

/**
 * @author qiaotong
 * @create 2019-07-04 9:25
 * @description
 */
public class DemoX {
    public static void main(String[] args) {
        X x = new X();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    x.addOne();
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("X = " + x.value);
    }
}
