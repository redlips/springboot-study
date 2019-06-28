package com.redlips.thread.scene._01;

import java.util.concurrent.CountDownLatch;

/**
 * @author qiaotong
 * @create 2019-06-19 14:11
 * @description 考试情景，线程间协调：一个或多个线程 等待另外N个线程执行完毕后再执行
 * CountDownLatch 是一个计数器，每执行完一个线程 计数器-1
 */
public class DemoTest {
    static final int COUNT = 20;
    static CountDownLatch countDownLatch = new CountDownLatch(COUNT);

    public static void main(String[] args) {
        new Thread(new TeacherRunnable(countDownLatch)).start();

        for (int i = 1; i <= COUNT; i++) {
            new Thread(new StudentRunnable(i, countDownLatch)).start();
        }

//        synchronized (DemoTest.class) {
//            try {
//                DemoTest.class.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    static class TeacherRunnable implements Runnable {
        CountDownLatch countDownLatch;

        TeacherRunnable(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println("老师发试卷");
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("老师收试卷");
        }
    }

    static class StudentRunnable implements Runnable {
        CountDownLatch countDownLatch;
        int number;

        StudentRunnable(int number, CountDownLatch countDownLatch) {
            this.number = number;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                System.out.println(String.format("学生 %d 写卷子", number));
                Thread.sleep(5000);
                System.out.println(String.format("学生 %d 交卷子", number));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }
    }
}
