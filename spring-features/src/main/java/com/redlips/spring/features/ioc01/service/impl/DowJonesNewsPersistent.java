package com.redlips.spring.features.ioc01.service.impl;

import com.redlips.spring.features.ioc01.pojo.FXNewsBean;
import com.redlips.spring.features.ioc01.service.IFXNewsPersistent;
import org.springframework.stereotype.Component;

/**
 * @author 花落孤忆
 * @create 2018-10-26 17:22
 * @description 道琼斯新闻 获取并存入本地数据库
 */
@Component
public class DowJonesNewsPersistent implements IFXNewsPersistent {
    @Override
    public FXNewsBean getNewsByPK(String newsId) {
        return null;
    }

    @Override
    public void persistNews(FXNewsBean newsBean) {

    }
}
