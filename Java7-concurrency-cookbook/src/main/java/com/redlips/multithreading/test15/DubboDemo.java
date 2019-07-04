package com.redlips.multithreading.test15;

import com.sun.deploy.nativesandbox.comm.Response;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qiaotong
 * @create 2019-07-04 17:14
 * @description Dubbo 将RPC调用 异步转同步的 DEMO
 */
public class DubboDemo {
    private final Lock lock = new ReentrantLock();

    private final Condition done = lock.newCondition();

    private Object response;

    // 调用方通过该方法等待结果
    Object get(int timeOut) throws TimeoutException {
        long start = System.nanoTime();
        lock.lock();

        try {
            while (!isDone()) {
                done.await(timeOut, TimeUnit.NANOSECONDS);
                long cur = System.nanoTime();

                if (isDone() || cur - start > timeOut)
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        if (!isDone())
            throw new TimeoutException();

        // 返回调用的结果...
        return null;
    }

    // RPC结果是否已经返回
    boolean isDone() {
        return response != null;
    }

    // RPC结果返回时，调用该方法
    private void doReceived(Response res) {
        lock.lock();

        try {
            response = res;

            done.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
