package com.redlips.java.features.currency;

import java.util.concurrent.ForkJoinPool;

/**
 * @author qiaotong
 * @create 2019-06-17 17:03
 * @description
 */
public class LostWakeUp {
    public static void main(String[] args) {
        Object object = new Object();
        int commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism();
        System.out.println(commonPoolParallelism);
        try {
            object.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
