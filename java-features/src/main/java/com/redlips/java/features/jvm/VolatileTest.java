package com.redlips.java.features.jvm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author qiaotong
 * @create 2019-05-13 14:38
 * @description 变量自增运算测试, 演示说明volatile只保证了可见性，不保证原子性
 */
public class VolatileTest {
    public static volatile int race = 0;
    public static void increase() {
        race++;
    }

    private static final int THREADS_COUNT = 20;
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_COUNT);
        for (int i = 0; i < THREADS_COUNT; i++) {
            executorService.execute(new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    increase();
                }
            }));
            System.out.println("当前线程名称：" + Thread.currentThread().getName());
        }
        System.out.println("当前线程组的数据 ：" + Thread.activeCount());
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        System.out.println(race);

    }
}
