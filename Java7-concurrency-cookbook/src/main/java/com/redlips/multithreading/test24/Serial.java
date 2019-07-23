package com.redlips.multithreading.test24;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qiaotong
 * @create 2019-07-17 10:02
 * @description 1.描述串行关系
 */
public class Serial {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1 的Thread " + Thread.currentThread().getName());
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return "hello world";
        })
                .thenApply((s) -> {
                    System.out.println("任务2 的Thread " + Thread.currentThread().getName());
                    return s + " QQ";})
                .thenApply(s -> {
                    System.out.println("任务3 的Thread " + Thread.currentThread().getName());
                    return s.toUpperCase();});

        System.out.println(future.join());
        System.out.println("xx");
    }
}
