package com.redlips.java.features.currency.futurepatternwithjdk;

import java.util.concurrent.*;

/**
 * @author 花落孤忆
 * @create 2018-09-21 14:49
 * @description 系统启动，调用Client发出请求，由于使用了JDK内置框架，Data(相当于Callable)、FutureData和Client（相当于FutureTask
 * ，Client用于开启线程开启任务装配RealData，FutureData用于控制线程)等对象
 * 就不在需要了，
 */
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 构造FutureTask
        FutureTask<String> future = new FutureTask<String>(new RealData("a"));

        ExecutorService executor = Executors.newFixedThreadPool(1);
        // 执行FutureTask，相当于上例中的client.request("a")发送请求
        executor.submit(future);
        System.out.println("请求完毕");

        try {
            // 这里依然可以做额外的数据操作，这里使用sleep代替其他业务逻辑的处理
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 相当于上例中的data.getResult()，取的call()方法的返回值
        // 如果call()方法没有执行完成，则依然会等待
        System.out.println("真实数据是 = " + future.get());
    }
}
