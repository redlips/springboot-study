package com.redlips.thread.scene;

/**
 * @author qiaotong
 * @create 2019-06-18 17:13
 * @description 线程在sleep或wait时，是处于无法交互的状态的，此时只能使用interrupt方法中断它，线程会被激活并收到中断异常
 */
public class SceneForth {
    private static String STR_FORMAT_IN = "线程 %s, 进入不可暂停区域 %d ...";
    private static String STR_FORMAT_OUT = "线程 %s, 退出不可暂停区域 %d ...";

    public static void main(String[] args) {
        StopByInterruptRunnable runnable = new StopByInterruptRunnable();
        Thread thread = new Thread(runnable);
        thread.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }

    static class StopByInterruptRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 进入暂停");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + " 收到中断异常");
                System.out.println(Thread.currentThread().getName() + " 做一些相关处理");
            }

            System.out.println("继续执行或选择退出");
        }
    }
}
