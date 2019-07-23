package com.redlips.multithreading.test18;

import java.util.concurrent.locks.StampedLock;

/**
 * @author qiaotong
 * @create 2019-07-09 10:30
 * @description 比读写锁更快的锁
 */
public class StampedLockDemo {
    final StampedLock stampedLock = new StampedLock();

    public void test() {
        // 获取悲观读锁
        long stamp = stampedLock.readLock();

        try {
            // 业务代码
        } finally {
            // 释放悲观读锁
            stampedLock.unlockRead(stamp);
        }

        // 获取写锁
        long stampRead = stampedLock.writeLock();
        try {
            // 业务代码
        } finally {
            // 释放写锁
            stampedLock.unlockWrite(stamp);
        }
    }
}
