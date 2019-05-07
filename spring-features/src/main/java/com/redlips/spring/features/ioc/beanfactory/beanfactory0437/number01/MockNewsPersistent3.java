package com.redlips.spring.features.ioc.beanfactory.beanfactory0437.number01;

import com.redlips.spring.features.base.IFXNewsPersistent;
import com.redlips.spring.features.pojo.FXNewsBean;
import org.springframework.beans.factory.ObjectFactory;

/**
 * @author qiaotong
 * @create 2019-02-15 13:45
 * @description
 */
public class MockNewsPersistent3 implements IFXNewsPersistent {
    private ObjectFactory objectFactory;
    @Override
    public FXNewsBean getNewsByPK(String newsId) {
        return null;
    }

    @Override
    public void persistNews(FXNewsBean newsBean) {

    }
    public void persistNews() {
        System.out.println("persist bean " + getNewsBean() );
    }

    public FXNewsBean getNewsBean() {
        return (FXNewsBean) objectFactory.getObject();
    }

    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }
}
