package com.redlips.spring.features.base;

import com.redlips.spring.features.pojo.FXNewsBean;

/**
 * @author 花落孤忆
 * @create 2018-10-26 17:04
 * @description FX新闻系统的新闻处理者, 公共接口定义
 */
public interface IFXNewsPersistent {
    /**
     * 根据ID获取新闻
     * @param newsId
     * @return
     */
    FXNewsBean getNewsByPK(String newsId);

    /**
     * 存入新闻到本地数据库
     * @param newsBean
     */
    void persistNews(FXNewsBean newsBean);
}
