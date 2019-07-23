package com.redlips.multithreading.test23;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author qiaotong
 * @create 2019-07-16 14:34
 * @description FutureTask应用
 */
public class TeaTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建任务T2 的 FutureTask
        FutureTask<String> ft2 = new FutureTask<>(new T2Task());

        // 创建任务T1 的 FutureTask
        FutureTask<String> ft1 = new FutureTask<>(new T1Task(ft2));

        // 线程T1执行任务ft1
        Thread t1 = new Thread(ft1);
        t1.start();
        // 线程T2执行任务ft2
        Thread t2 = new Thread(ft2);
        t2.start();

        // 等待T1执行结果
        System.out.println(ft2.isDone());
        System.out.println(ft1.get());

    }
}
