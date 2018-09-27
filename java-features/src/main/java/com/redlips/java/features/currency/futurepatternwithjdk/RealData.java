package com.redlips.java.features.currency.futurepatternwithjdk;

import java.util.concurrent.Callable;

/**
 * @author 花落孤忆
 * @create 2018-09-21 15:23
 * @description 真实数据，其构造是比较慢的
 */
public class RealData implements Callable<String> {
    private String para;

    // 改造后的构造函数中，RealData的构造变得非常快，因为其主要业务逻辑被移动到call()方法内，并通过call()方法返回
    public RealData(String para) {
        this.para = para;
    }

    @Override
    public String call() throws Exception {
        // 这里是真实的业务逻辑，其执行可能很慢
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

        return sb.toString();
    }
}
