package com.redlips.multithreading.test02;

/**
 * @author qiaotong
 * @create 2019-07-03 16:22
 * @description
 */
public class Test {
    public static void main(String[] args) {
        Thread th = Thread.currentThread();
        while(true) {
            if(th.isInterrupted()) {
                break;
            }
            // 省略业务代码无数
            try {
                Thread.sleep(100);
                th.interrupt();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

//        new Thread(th::interrupt).start();

        System.out.println("xxx");
    }
}
