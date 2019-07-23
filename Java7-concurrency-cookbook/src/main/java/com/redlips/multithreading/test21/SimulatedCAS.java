package com.redlips.multithreading.test21;

/**
 * @author qiaotong
 * @create 2019-07-12 13:40
 * @description
 */
public class SimulatedCAS {
    volatile int count;

    public int getCount() {
        return count;
    }

    // 自旋操作，实现count+1
    void addOne() {
        int newValue;
        int oldValue;
        do {
            oldValue = count;
            newValue = oldValue + 1;
        } while (oldValue != cas(oldValue, newValue));
    }



    synchronized int cas(int expect, int newValue) {
        int curValue = count;

        if (expect == curValue)
            count = newValue;

        // 返回写入前的值
        return curValue;
    }
}
