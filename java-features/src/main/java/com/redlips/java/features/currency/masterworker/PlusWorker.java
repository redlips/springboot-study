package com.redlips.java.features.currency.masterworker;

/**
 * @author: 花落孤忆
 * @createTime: 2018-10-13 22:51
 * @description: 应用层的Worker，进行立方运算的工作
 */
public class PlusWorker extends Worker {
    @Override
    public Object handle(Object input) {
        Integer i = (Integer) input;
        return i * i * i;
    }
}
