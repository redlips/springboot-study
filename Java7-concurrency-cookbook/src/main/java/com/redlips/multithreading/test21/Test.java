package com.redlips.multithreading.test21;

/**
 * @author qiaotong
 * @create 2019-07-12 11:24
 * @description
 */
public class Test {
    SimulatedCAS count = new SimulatedCAS();
    void add10K() {
        int idx = 0;
        while (idx++ < 10000)
            count.addOne();
    }
    public long getCount() {
        return count.getCount();
    }
    public static void main(String[] args) {
//        Test test = new Test();
//        CountDownLatch countDownLatch = new CountDownLatch(100);
//
//        for (int i = 0; i < 100; i++) {
//            Thread thread = new Thread(() -> {
//                test.add10K();
//                countDownLatch.countDown();
//            });
//            thread.start();
//        }
//
//        try {
//            countDownLatch.await();
//            System.out.println(test.getCount());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        int i = 1;
        int j = i++;
        System.out.println("i = " + i);
        System.out.println("j = " + j);

    }

}
