package com.redlips.multithreading.test25;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author qiaotong
 * @create 2019-07-19 16:38
 * @description 使用CompletionService实现Dubbo Forking模式
 * 批量异步任务模型
 */
public class DubboForkClusterDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ExecutorCompletionService<Integer> es = new ExecutorCompletionService<>(executorService);

        // Future对象列表
        List<Future<Integer>> futures = new ArrayList<>(3);

        // 提交异步任务
        futures.add(es.submit(() -> geocoderByS1()));
        futures.add(es.submit(() -> geocoderByS2()));
        futures.add(es.submit(() -> geocoderByS3()));
        // 获取最快返回的任务执行结果
        Integer r = null;

        try {
            for (int i = 0; i < 3; i++) {
                r = es.take().get();

                if (r != null)
                    break;
            }
        } catch (Exception e) {

        } finally {
            // 取消所有任务
            for (Future<Integer> future : futures) {
                future.cancel(true);
            }
        }

        System.out.println(r);
//        executorService.shutdown();

    }

    private static Integer geocoderByS3() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "任务中断" + e.getCause().getMessage());
        }
        return 1;
    }

    private static Integer geocoderByS2() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "任务中断" + e.getCause().getMessage());
        }

        return 1;
    }

    private static Integer geocoderByS1() {
        try {
            TimeUnit.SECONDS.sleep(12);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "任务中断" + e.getCause().getMessage());
        }
        return 1;
    }
}
