package com.redlips.multithreading.test02;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author qiaotong
 * @create 2019-07-03 10:07
 * @description
 */
public class SafeWM {
    // 库存上限
    private final AtomicLong upper = new AtomicLong(0);
    // 库存下限
    private final AtomicLong lower = new AtomicLong(0);

    // 设置库存上限
    synchronized void setUpper(long v) {
        if (v < lower.get())
            throw new IllegalArgumentException();
        upper.set(v);
    }

    // 设置库存下限
    synchronized void setLower(long v) {
        if (v > upper.get())
            throw new IllegalArgumentException();
        lower.set(v);
    }
}
