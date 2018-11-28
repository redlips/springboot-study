package com.redlips.spring.features.ioc01.service;

import com.redlips.spring.features.ioc01.pojo.FXNewsBean;

/**
 * @author 花落孤忆
 * @create 2018-10-26 17:04
 * @description
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
