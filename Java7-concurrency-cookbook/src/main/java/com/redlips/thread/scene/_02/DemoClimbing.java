package com.redlips.thread.scene._02;

import java.util.concurrent.CyclicBarrier;

/**
 * @author qiaotong
 * @create 2019-06-19 16:39
 * @description 栅栏，任意一线程没完成，所有线程都要等待
 */
public class DemoClimbing {
    static final int COUNT = 5;
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(COUNT, () -> System.out.println("为大家唱歌"));
    public static void main(String[] args) {
        for (int i = 1; i <= COUNT; i++) {
            new Thread(new ClimberRunnable(i, cyclicBarrier)).start();
        }

        synchronized (DemoClimbing.class) {
            try {
                DemoClimbing.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    static class ClimberRunnable implements Runnable {
        CyclicBarrier cyclicBarrier;
        int number;

        ClimberRunnable(int number, CyclicBarrier cyclicBarrier) {
            this.number = number;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println(String.format("登山者 %d 前往山腰", number));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("登山者 %d 到达山腰", number));
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(String.format("登山者 %d 前往山顶", number));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("登山者 %d 到达山顶", number));
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(String.format("登山者 %d 前往山脚", number));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("登山者 %d 到达山脚", number));
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(String.format("登山者 %d 登山结束", number));

        }
    }
}
