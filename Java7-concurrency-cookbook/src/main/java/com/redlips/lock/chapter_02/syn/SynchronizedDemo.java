package com.redlips.lock.chapter_02.syn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author qiaotong
 * @create 2019-06-05 16:57
 * @description
 */
public class SynchronizedDemo {
    private static int counter = 0;
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        SynchronizedDemo demo = new SynchronizedDemo();
        for (int i = 0; i < 10; i++) {
            threadPool.execute(() -> demo.increase());
        }

        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("最终的结果是 counter = " + counter);
    }

    private synchronized void increase() {
        for (int i = 0; i < 10000 ; i++) {
            counter++;
        }
        System.out.println("线程" + Thread.currentThread().getName() + "为计数器增加10000结束");
    }

}
