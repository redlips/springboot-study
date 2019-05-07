package com.redlips.spring.features.ioc.iocserviceprovider;

import com.redlips.spring.features.base.IFXNewsListener;
import org.springframework.stereotype.Component;

/**
 * @author 花落孤忆
 * @create 2018-10-26 17:19
 * @description 道琼斯新闻监听者
 */
@Component
public class DowJonesNewsListener implements IFXNewsListener {
    @Override
    public String[] getAvailableNewsIds() {
        return new String[0];
    }
}
