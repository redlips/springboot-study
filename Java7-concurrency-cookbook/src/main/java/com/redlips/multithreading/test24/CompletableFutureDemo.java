package com.redlips.multithreading.test24;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author qiaotong
 * @create 2019-07-16 16:55
 * @description CompletableFuture Demo
 */
public class CompletableFutureDemo {
    public static void main(String[] args) {
        // 任务一：洗水壶 -> 烧开水
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            System.out.println("T1 洗水壶");
            sleep(1);

            System.out.println("T1 烧开水");
            sleep(15);
        });

        // 任务二：洗茶壶 - 洗茶杯 - 拿茶叶
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("T2 洗茶壶");
            sleep(1);

            System.out.println("T2 洗茶杯");
            sleep(1);

            System.out.println("T2 拿茶叶");
            sleep(1);

            return "龙井";
        });

        // 任务三：将任务一、任务二完成后执行：泡茶
        CompletableFuture<String> future3 = future1.thenCombine(future2, (__, tf) -> {
            System.out.println("拿到茶叶 " + tf);
            System.out.println("泡茶");
            return "上茶：" + tf;
        });

        // 等待任务三执行结果
        System.out.println(future3.join());

        try {
            System.out.println(future3.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }

    public static void sleep(int count) {
        try {
            TimeUnit.SECONDS.sleep(count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
