package com.redlips.test03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author qiaotong
 * @create 2019-06-27 11:52
 * @description
 */
public class Demo {
    public static void main(String[] args) {
        Account a = new Account(200);
        Account b = new Account(200);
        Account c = new Account(200);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            a.transfer(b, 100);
        });
        executorService.execute(() -> {
            b.transfer(c, 100);
        });

//        Thread thread1 = new Thread(() -> {
//            a.transfer(b, 100);
//        });
//
//        Thread thread2 = new Thread(() -> {
//            b.transfer(c, 100);
//        });


//        thread1.start();
//        thread2.start();

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("a的账户余额 = " + a.getBalance());
        System.out.println("b的账户余额 = " + b.getBalance());
        System.out.println("c的账户余额 = " + c.getBalance());
    }
}
