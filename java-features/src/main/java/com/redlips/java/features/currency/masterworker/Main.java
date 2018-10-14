package com.redlips.java.features.currency.masterworker;

import java.util.Map;
import java.util.Set;

/**
 * @author: 花落孤忆
 * @createTime: 2018-10-13 22:54
 * @description: Master-Worker的main函数
 */
public class Main {
    public static void main(String[] args) {
        // 固定使用5个Worker,并指定Worker
        Master m = new Master(new PlusWorker(), 5);
        // 提交100个子任务
        for (int i = 0; i < 100; i++) m.submit(i);
        // 开始计算
        m.execute();
        // 最终计算结果保存在此
        int re = 0;
        Map<String, Object> resultMap = m.getResultMap();
        while (resultMap.size() > 0 || !m.isComplete()) {
            // 不需要等待所有Worker都执行完，即可
            Set<String> keys = resultMap.keySet();
            // 开始计算最终结果
            String key = null;
            for (String k : keys) {
                key = k;
                break;
            }
            Integer i = null;
            if(key != null) {
                i = (Integer) resultMap.get(key);
            }
            if (i != null) {
                re += i;
            }
            if(key != null) {
                // 移除已经被算过的项
                resultMap.remove(key);
            }
        }

        System.out.println(re);
    }
}
