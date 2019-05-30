package com.redlips.lock.chapter_02;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qiaotong
 * @create 2019-05-27 16:07
 * @description 悲观锁，示例
 */
public class LockDemo {
    public static void main(String[] args) {

    }

    /** 悲观锁示例 */
    public synchronized void testPessimism() {
        // 操作同步资源
    }

    private final ReentrantLock reentrantLock = new ReentrantLock();
    public void modifyPublicResource() {
        reentrantLock.lock();
        try {
            // 操作同步资源
        } finally {
            reentrantLock.unlock();
        }
    }

    /** 乐观锁示例 */
    private AtomicInteger atomicInteger = new AtomicInteger();
    public void incrementAndGet() {
        atomicInteger.incrementAndGet();
    }
}
