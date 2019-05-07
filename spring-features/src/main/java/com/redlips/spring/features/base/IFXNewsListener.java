package com.redlips.spring.features.base;

/**
 * @author 花落孤忆
 * @create 2018-10-26 17:01
 * @description FX系统的新闻监听者,公共的接口定义
 */
public interface IFXNewsListener {
    /**
     * 获取可用新闻
     * @return
     */
    String[] getAvailableNewsIds();
}
