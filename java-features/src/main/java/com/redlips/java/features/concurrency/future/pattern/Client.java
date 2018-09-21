package com.redlips.java.features.concurrency.future.pattern;

/**
 * @author 花落孤忆
 * @create 2018-09-21 16:32
 * @description 返回Data对象，立即返回FutureData，并开启ClientThread线程装配RealData
 */
public class Client {
    public Data request(final String queryStr) {
        final FutureData futureData = new FutureData();

        new Thread() {

        }
    }
}
