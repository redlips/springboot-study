package com.redlips.multithreading.test19.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qiaotong
 * @create 2019-07-10 10:58
 * @description 优化方案：加入队列，提高生产消费速度；加入栅栏，实现线程同步
 */
public class OrderOptimizationDemo {
    boolean isExistAccount = true;
    Object payOrders;
    Object deliverOrder;
    Object diff;

    public void checkAccount() {
        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);

        while (isExistAccount) {
            // 计数器初始化为2
            CountDownLatch countDownLatch = new CountDownLatch(2);

            // 检查未对账订单
            executor.execute(() -> {
                payOrders = getPayOrders();
                if (payOrders == null) {
                    isExistAccount = false;
                }
                countDownLatch.countDown();
            });

            // 查询派送单
            executor.execute(() -> {
                deliverOrder = getDOrder();
                countDownLatch.countDown();
            });

            // 等待 t1 t2结束
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 执行对账操作
            diff = check(payOrders, deliverOrder);
            // 差异写入差异库
            save(diff);

            // 优化方案：是否可以让查询操作、对账操作并行执行
        }
    }

    private void save(Object diff) {
        // 入库逻辑
    }

    private Object check(Object payOrders, Object deliverOrder) {
        return new Object();
    }

    private Object getDOrder() {
        return null;
    }

    private Object getPayOrders() {
        return new Object();
    }
}
