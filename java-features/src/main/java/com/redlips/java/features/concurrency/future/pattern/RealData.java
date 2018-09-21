package com.redlips.java.features.concurrency.future.pattern;

/**
 * @author 花落孤忆
 * @create 2018-09-21 15:23
 * @description 真实数据，其构造是比较慢的
 */
public class RealData implements Data{
    protected final String result;

    public RealData(String para) {
        // RealData的构造很慢，需要用户等待很久，这里使用sleep模拟
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 10; i++) {
            sb.append(para);
            try {
                // 这里使用sleep，代替一个很慢的操作过程
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.result = sb.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}
