package com.redlips.multithreading.test22;

/**
 * @author qiaotong
 * @create 2019-07-15 11:28
 * @description 一般的池化资源
 */
public class CommonPoolResource {
    Object acquire() {
        return new Object();
    }

    // 释放资源
    void release(Object o) {

    }
}
