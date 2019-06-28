package com.redlips.thread.scene;

/**
 * @author qiaotong
 * @create 2019-06-18 11:19
 * @description 任务发出停止指令， 线程在预设的地点检测flag，来决定是否停止
 */
public class SceneOne {
    public static void main(String[] args) {
        StopByFlagRunnable runnable = new StopByFlagRunnable();
        new Thread(runnable).start();
        runnable.tellToStop();
    }

    static class StopByFlagRunnable implements Runnable {
        volatile boolean stop;

        void tellToStop() {
            System.out.println("发出停止命令");
            stop = true;
        }

        @Override
        public void run() {
            System.out.println("进入不可停止区域... 01");
            doingLongTime(3);
            System.out.println("退出不可停止区域... 01");
            System.out.println(String.format("检测停止标记 stop = %s", String.valueOf(stop)));

            if (stop) {
                System.out.println("停止执行");
                return;
            }

            System.out.println("进入不可停止区域... 02");
            doingLongTime(3);
            System.out.println("退出不可停止区域... 02");
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
