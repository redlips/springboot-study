package com.redlips.multithreading.test16;

import java.util.concurrent.Semaphore;

/**
 * @author qiaotong
 * @create 2019-07-05 16:48
 * @description 部分功能展现 互斥锁
 */
public class SemaphoreDemo {
    static int count;
    static Semaphore semaphore = new Semaphore(1);

    static void addOne() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            count++;
        } finally {
            semaphore.release();
        }

    }
}
