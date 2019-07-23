package com.redlips.multithreading.test21;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author qiaotong
 * @create 2019-07-15 10:15
 * @description
 */
public class SafeWM {
    class WMRange {
        final int upper;
        final int lower;

        public WMRange(int upper, int lower) {
            this.upper = upper;
            this.lower = lower;
        }
    }

    final AtomicReference<WMRange> rf = new AtomicReference<>(new WMRange(0, 0));

    // 设置库存上限
    void setUpper(int v) {
        WMRange nr;
        WMRange or;
        do {
            or = rf.get();
            if (v < or.lower)
                throw new IllegalArgumentException();
            nr = new WMRange(v, or.lower);
        } while (!rf.compareAndSet(or, nr));
    }
}
