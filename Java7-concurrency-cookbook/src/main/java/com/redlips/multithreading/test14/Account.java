package com.redlips.multithreading.test14;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qiaotong
 * @create 2019-07-04 13:47
 * @description
 */
public class Account {
    public Account(int balance, String name) {
        this.balance = balance;
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    private String name;
    private int balance;
    private final Lock lock = new ReentrantLock();

    void transfer(Account target, int amount) throws InterruptedException {
        while (true) {
            if (this.lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                try {
                    if (target.lock.tryLock(30, TimeUnit.MILLISECONDS)) {
                        try {
                            this.balance -= amount;
                            target.balance += amount;
                            break;
                        } finally {
                            System.out.println(target.name + "释放锁" + System.currentTimeMillis());
                            target.lock.unlock();
                        }
                    }
                } finally {
                    System.out.println(this.name + "释放锁"+ System.currentTimeMillis());
                    this.lock.unlock();
                }
            }
        }
    }
}
