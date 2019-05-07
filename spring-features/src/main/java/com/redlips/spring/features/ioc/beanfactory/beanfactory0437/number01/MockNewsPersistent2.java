package com.redlips.spring.features.ioc.beanfactory.beanfactory0437.number01;

import com.redlips.spring.features.base.IFXNewsPersistent;
import com.redlips.spring.features.pojo.FXNewsBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author qiaotong
 * @create 2019-02-15 10:52
 * @description
 */
public class MockNewsPersistent2 implements IFXNewsPersistent, BeanFactoryAware {
    private BeanFactory beanFactory;
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
        return (FXNewsBean) beanFactory.getBean("newsBean");
    }
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
