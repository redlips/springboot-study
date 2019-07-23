package com.redlips.multithreading.test25;

import java.util.concurrent.*;

/**
 * @author qiaotong
 * @create 2019-07-19 15:31
 * @description 使用CompletionService完成询价系统，底层维护一个队列
 */
public class CompletionServiceDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletionService<Object> cs = new ExecutorCompletionService<>(executorService);

        // 异步向电商一询价
        cs.submit(() -> getPriceByS1());
        cs.submit(() -> getPriceByS1());
        cs.submit(() -> getPriceByS1());

        for (int i = 0; i < 3; i++) {
            Object o = cs.take().get();
            System.out.println(o);
            executorService.execute(() -> save(o));
        }

    }

    private static void save(Object o) {
    }

    private static Object getPriceByS1() {
        return 1;
    }
}
