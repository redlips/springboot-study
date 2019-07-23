package com.redlips.multithreading.test18;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

/**
 * @author qiaotong
 * @create 2019-07-09 11:41
 * @description
 */
public class StampedInterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        final StampedLock lock
                = new StampedLock();
        Thread T1 = new Thread(()->{
            // 获取写锁
            lock.writeLock();
            // 永远阻塞在此处，不释放写锁
            LockSupport.park();
        }, "线程1");
        T1.start();
        // 保证 T1 获取写锁
        Thread.sleep(100);

        Thread T2 = new Thread(lock::readLock, "线程2");
        T2.start();
        // 保证 T2 阻塞在读锁
        Thread.sleep(100);
        // 中断线程 T2
        // 会导致线程 T2 所在 CPU 飙升
        T2.interrupt();
        T2.join();

    }
}
