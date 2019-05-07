package com.redlips.spring.features.ioc.iocserviceprovider;

import com.redlips.spring.features.base.IFXNewsListener;

/**
 * @author 花落孤忆
 * @create 2018-11-26 17:29
 * @description MarketWin24新闻监听者
 */
public class MarketWin24NewsListener implements IFXNewsListener {
    @Override
    public String[] getAvailableNewsIds() {
        return new String[0];
    }
}
