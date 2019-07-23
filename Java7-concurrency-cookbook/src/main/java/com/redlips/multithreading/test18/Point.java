package com.redlips.multithreading.test18;

import java.util.concurrent.locks.StampedLock;

/**
 * @author qiaotong
 * @create 2019-07-09 10:58
 * @description
 */
public class Point {
    private double x, y;

    final StampedLock sl = new StampedLock();

    // 写锁，独占
    void move(double deltaX, double deltaY) {
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    // 只读方法，计算到原点的距离
    double distanceFromOrigin() {
        // 乐观读
        long stampRead = sl.tryOptimisticRead();
        // 将共享变量x,y读入局部变量
        // 读的过程数据可能会被修改
        double curX = x, curY = y;

        // 校验,如果存在写数据 validate返回false
        if (!sl.validate(stampRead)) {
            // 升级为悲观读锁
            stampRead = sl.readLock();
            try {
                curX = x;
                curY = y;
            } finally {
                // 释放悲观读锁
                sl.unlockRead(stampRead);
            }
        }

        return Math.sqrt(curX * curX + curY * curY);
    }

    // 锁升级
    void moveIfAtOrigin(double newX, double newY) {
        long stamp = sl.readLock();

        try {
            while (x == 0.0 && y == 0.0) {
                long ws = sl.tryConvertToWriteLock(stamp);
                if (ws != 0L) {
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    sl.unlockRead(stamp);
                    stamp = sl.writeLock();
                }
            }
        } finally {
            sl.unlock(stamp);
        }
    }

//    public static void main(String[] args) {
//        Point point = new Point();
//        int i = 0;
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        for (int j = 0; j < 10; j++) {
//            i++;
//            if (i % 2 == 0) {
//                int finalI = i;
//                executorService.execute(() -> {
//                    point.setX(2 * finalI);
//                    point.setY(3 * finalI);
//                });
//            }
//            executorService.execute(() -> {
//                System.out.println(point.distanceFromOrigin());
//            });
//        }
//        executorService.shutdown();
//        try {
//            executorService.awaitTermination(10, TimeUnit.DAYS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(point.distanceFromOrigin());
//    }
}
