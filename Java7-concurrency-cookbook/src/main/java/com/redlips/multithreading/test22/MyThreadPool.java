package com.redlips.multithreading.test22;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author qiaotong
 * @create 2019-07-15 16:03
 * @description 简化的线程池
 */
public class MyThreadPool {
    // 利用阻塞队列实现生产者-消费者模式
    BlockingQueue<Runnable> workQueue;
    // 保存内部工作线程
    List<WorkThread> threads = new ArrayList<>();

    public MyThreadPool(int poolSize, BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        for (int i = 0; i < poolSize; i++) {
            WorkThread workThread = new WorkThread("线程"+i);
            workThread.start();
            threads.add(workThread);
        }
    }

    // 提交任务
    void execute(Runnable runnable) throws InterruptedException {
        workQueue.put(runnable);
    }

    // 工作线程负责消费任务，并执行任务
    class WorkThread extends Thread {
        public WorkThread(String name) {
            super(name);
        }

        public void run() {
            // 循环取任务并执行
            while (true) {
                try {
                    Runnable take = workQueue.take();
                    take.run();
                } catch (RuntimeException e) {
                    // 按需处理异常
                    e.printStackTrace();
                } catch (Throwable throwable) {
                    // 按需处理异常
                    throwable.printStackTrace();
                }
            }
        }
    }

    /** 使用示例 */
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(2);
        // 创建线程池
        MyThreadPool myThreadPool = new MyThreadPool(10, workQueue);
        myThreadPool.execute(() -> System.out.println("hell0"));


        ExecutorService executorService = Executors.newFixedThreadPool(2);

    }

}
