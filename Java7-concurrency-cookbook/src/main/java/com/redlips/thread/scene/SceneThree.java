package com.redlips.thread.scene;

/**
 * @author qiaotong
 * @create 2019-06-18 16:57
 * @description join方法插入到当前执行线程前面，等它执行完，自己才会继续执行
 */
public class SceneThree {
    private static String STR_FORMAT_IN = "线程 %s, 进入不可暂停区域 %d ...";
    private static String STR_FORMAT_OUT = "线程 %s, 退出不可暂停区域 %d ...";

    public static void main(String[] args) {
        JoinRunnable runnable = new JoinRunnable();
        Thread thread = new Thread(runnable);

        System.out.println("线程" + thread.getName() + "开始执行");
        thread.start();

        {
            try {
                System.out.println("线程" + Thread.currentThread().getName() + "开始处理任务3秒");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("终于轮到我了");
    }

    static class JoinRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println(String.format(STR_FORMAT_IN, Thread.currentThread().getName(), 1));
            doingLongTime(5);
            System.out.println(String.format(STR_FORMAT_OUT, Thread.currentThread().getName(), 1));
        }
        private void doingLongTime(int i) {
            try {
                Thread.sleep(i * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
