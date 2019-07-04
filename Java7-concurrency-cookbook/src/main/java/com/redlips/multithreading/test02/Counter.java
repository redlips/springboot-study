package com.redlips.multithreading.test02;

/**
 * @author qiaotong
 * @create 2019-07-03 10:00
 * @description 计数器，封装共享变量
 */
public class Counter {
    private long value;

    synchronized long get() {
        return value;
    }

    synchronized long addValue() {
        return ++value;
    }
}
