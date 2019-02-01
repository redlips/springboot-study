package com.redlips.java7.concurrency.abstractQueueS;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author qiaotong
 * @create 2019-01-21 14:39
 * @description
 */
public class TestMutex {
    private static CyclicBarrier barrier = new CyclicBarrier(31);
    private static int a = 0;
    private static Mutex mutex = new Mutex();

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    increment1();
                }

                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        barrier.await();
        System.out.println("加锁前，a="+a);

        // 加锁后
        barrier.reset();
        a = 0;
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    increment2();
                }

                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        barrier.await();
        System.out.println("加锁后，a="+a);
    }

    private static void increment2() {
        mutex.lock();
        a++;
        mutex.unLock();
    }

    /**
     * 没有同步措施
     */
    public static void increment1(){
        a++;
    }
}
