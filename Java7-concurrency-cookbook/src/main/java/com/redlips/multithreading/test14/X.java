package com.redlips.multithreading.test14;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qiaotong
 * @create 2019-07-04 9:19
 * @description
 */
public class X {
    private final Lock rtl = new ReentrantLock();
    int value;

    public int get() {
        rtl.lock();
        try {
            return value;
        } finally {
            rtl.unlock();
        }
    }

    public void addOne() {
        rtl.lock();
        try {
            value = 1 + get();
        } finally {
            rtl.unlock();
        }
    }

}
