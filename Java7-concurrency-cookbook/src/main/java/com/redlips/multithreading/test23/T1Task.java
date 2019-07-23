package com.redlips.multithreading.test23;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author qiaotong
 * @create 2019-07-16 14:27
 * @description
 */
public class T1Task implements Callable<String> {
    FutureTask<String> ft2;

    public T1Task(FutureTask<String> ft2) {
        this.ft2 = ft2;
    }

    @Override
    public String call() throws Exception {
        System.out.println("T1 洗水壶");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("T1 烧开水");
        TimeUnit.SECONDS.sleep(15);

        // 获取T2线程的执行结果，拿到茶叶
        String tf = ft2.get();
        System.out.println("T1 拿到茶叶：" + tf);

        System.out.println("T1 开始泡茶");

        return "T1 上茶 " + tf;
    }
}
