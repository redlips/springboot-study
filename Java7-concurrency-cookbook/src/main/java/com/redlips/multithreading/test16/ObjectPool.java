package com.redlips.multithreading.test16;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * @author qiaotong
 * @create 2019-07-05 17:15
 * @description 对象池， 利用信号量限流功能
 */
public class ObjectPool<T, R> {
    final List<T> pool;
    // 信号量实现限流
    final Semaphore semaphore;

    ObjectPool(int size, T t) {
        pool = new Vector<>();
        for (int i = 0; i < size; i++) {
            pool.add(t);
        }
        semaphore = new Semaphore(size);
    }

    // 利用对象池中的对象调用func
    R exec(Function<T, R> function) {
        T t = null;
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            t = pool.remove(0);
            return function.apply(t);
        } finally {
            pool.add(t);
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        ObjectPool<Long, String> pool = new ObjectPool<>(10, (long) 2);
        // 通过对象池获取t，之后执行
        pool.exec(t -> {
            System.out.println(t);
            return t.toString();
        });
    }
}
