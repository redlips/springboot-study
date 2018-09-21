package com.redlips.java.features.concurrency.future.pattern;

/**
 * @author 花落孤忆
 * @create 2018-09-21 16:07
 * @description Future数据，构造很快，但是是一个虚拟的数据，需要装配RealData
 */
public class FutureData implements Data {
    protected RealData realData = null; // FutureData是RealData的包装
    protected boolean isReady = false;

    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }
        this.realData = realData;
        isReady = true;
        notifyAll();    // RealData已经被注入，通知getResult()
    }


    public synchronized String getResult() {
        // 等待RealData构造完成
        while (!isReady) {
            try {
                // 还未装入RealData，一直等待，直到RealData被注入
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.result;
    }
}
