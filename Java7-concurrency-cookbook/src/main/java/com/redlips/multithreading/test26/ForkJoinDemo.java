package com.redlips.multithreading.test26;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author qiaotong
 * @create 2019-07-25 11:19
 * @description 分治任务计算框架
 */
public class ForkJoinDemo {
    public static void main(String[] args) {
        // 创建分治任务线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        // 创建分治任务
        Pibonacci fib = new Pibonacci(2);
        // 启动分治任务
        Integer result = forkJoinPool.invoke(fib);
        System.out.println(result);
    }

    static class Pibonacci extends RecursiveTask<Integer> {
        final int n;

        Pibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1)
                return n;
            Pibonacci f1 = new Pibonacci(n -1);
            // 创建子任务
            f1.fork();
            Pibonacci f2 = new Pibonacci(n -2);
            // 等待子任务结果，并合并结果
            return f2.compute() + f1.join();
        }
    }
}
