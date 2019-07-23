package com.redlips.multithreading.test19.countdownlatch;

import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qiaotong
 * @create 2019-07-11 16:51
 * @description 再次进行优化，添加栅栏，使查询操作和对账操作可以并行
 */
public class OrderCyclicOptimization {
    // 订单队列
    Vector<Object> pos;
    // 派送单队列
    Vector<Object> dos;
    // 执行回调的线程池
    ExecutorService executorService = Executors.newFixedThreadPool(1);
    final CyclicBarrier barrier = new CyclicBarrier(2, () -> executorService.execute(this::check));

    // 对账操作
    private void check() {
        Object o1 = pos.remove(0);
        Object o2 = dos.remove(0);
        // 执行对账操作
        Object object = check(o1, o2);
        // 写入差异库
        save(object);
    }

    private void save(Object object) {
    }

    private Object check(Object o1, Object o2) {
        return new Object();
    }

    // 查询操作
    void checkAll() {
        // 循环查询订单库
        Thread thread1 = new Thread(() -> {
            while (/** 条件 */ true) {
                pos.add(getPOrders());
                // 等待
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();

        // 循环查询运单库
        Thread thread2 = new Thread(() -> {
            while (/** 条件 */ true) {
                dos.add(getDOrders());
                // 等待
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        thread2.start();
    }

    private Object getDOrders() {
        return new Object();
    }

    private Object getPOrders() {
        return new Object();
    }

}
