package com.redlips.multithreading.test01;

/**
 * @author qiaotong
 * @create 2019-06-27 11:53
 * @description
 */
public class Account {
    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    synchronized void transfer(Account target, int amt) {
        if (this.balance > amt) {
            this.balance -= amt;
            target.balance += amt;
        }
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
