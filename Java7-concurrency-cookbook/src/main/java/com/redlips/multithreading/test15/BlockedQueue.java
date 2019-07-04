package com.redlips.multithreading.test15;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qiaotong
 * @create 2019-07-04 16:30
 * @description 阻塞队列，多个条件变量
 */
public class BlockedQueue<T> {
    private final Lock lock = new ReentrantLock();
    // 条件变量，队列不满
    private Condition notFull = lock.newCondition();
    // 条件变量，队列不空
    private Condition notEmpty = lock.newCondition();

    // 入队
    void enq(T t) {
        lock.lock();

        try {
            // 不满足条件
            try {
                notFull.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 入队操作

            // 通知可出队
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    void deq() {
        lock.lock();

        try {
            // 不满足条件，是空队列
            try {
                notEmpty.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 出队操作

            // 通知可入队
            notFull.signal();
        } finally {
            lock.unlock();
        }
    }
}
