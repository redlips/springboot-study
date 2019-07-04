package com.redlips.multithreading.test14;

/**
 * @author qiaotong
 * @create 2019-07-04 13:53
 * @description
 */
public class DemoAccount {
    public static void main(String[] args) throws InterruptedException {
        Account accountA = new Account(2000, "A");
        Account accountB = new Account(2000, "B");

        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(() -> {
                try {
                    accountA.transfer(accountB, 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            thread.join();
        }

        System.out.println(accountA.getBalance());
        System.out.println(accountB.getBalance());
    }
}
