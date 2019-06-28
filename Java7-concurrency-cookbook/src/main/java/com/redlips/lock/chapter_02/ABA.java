package com.redlips.lock.chapter_02;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author qiaotong
 * @create 2019-01-22 10:56
 * @description
 */
public class ABA {
    private static AtomicInteger atomicInteger = new AtomicInteger(100);
    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 0);


    public static void main(String[] args) throws InterruptedException {
//        Thread t1 = new Thread(() -> {
//            atomicInteger.compareAndSet(100, 101);
//            atomicInteger.compareAndSet(101, 100);
//        });
//
//        Thread t2 = new Thread(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            boolean b = atomicInteger.compareAndSet(100, 101);
//            System.out.println(b);
//        });
//
//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();


        Thread t3 = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程3 after sleep : stamp = " + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(100, 101,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            atomicStampedReference.compareAndSet(101, 100,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
        });

        Thread t4 = new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println("before sleep : stamp = " + stamp);
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("after sleep : stamp = " + atomicStampedReference.getStamp());
            boolean b = atomicStampedReference.compareAndSet(100, 101,
                    stamp, stamp + 1);
            System.out.println(b);
        });

        t3.start();
        t4.start();
    }
}
