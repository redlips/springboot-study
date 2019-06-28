package com.redlips.thread.scene;

/**
 * @author qiaotong
 * @create 2019-06-18 14:51
 * @description 外部发出指令：暂停，线程执行完当前任务主动暂停。但恢复无法自主进行，只能发出指令，由操作系统恢复线程执行
 * 在预设的地点检测flag。然后就是wait/notify配合使用。
 */
public class SceneTwo {
    public static void main(String[] args) {
        PauseByFlagRunnable runnable = new PauseByFlagRunnable();
        new Thread(runnable).start();
        runnable.tellToPause();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runnable.tellToResume();
    }

    static class PauseByFlagRunnable implements Runnable {
        volatile boolean pause;

        void tellToPause() {
            System.out.println("发出暂停指令");
            pause = true;
        }

        void tellToResume() {
            synchronized (this) {
                System.out.println("发出唤醒指令");
                this.notify();
            }
        }

        @Override
        public void run() {
            System.out.println("进入不可暂停区域... 01");
            doingLongTime(3);
            System.out.println("退出不可暂停区域... 01");
            System.out.println(String.format("检测暂停标记 pause = %s", String.valueOf(pause)));

            if (pause) {
                System.out.println("暂停执行");
                try {
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("恢复执行");
            }

            System.out.println("进入不可暂停区域... 02");
            doingLongTime(3);
            System.out.println("退出不可暂停区域... 02");
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
