package com.redlips;

import java.util.concurrent.TimeUnit;

/**
 * @author qiaotong
 * @create 2019-01-16 16:46
 * @description
 */
public class Main {
    public static void main(String[] args) {
        Thread task = new PrimeGenerator();
        task.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 中断
        task.interrupt();
    }
}
