package com.redlips.spring.features.ioc.beanfactory.beanfactory0437.number01;

import com.redlips.spring.features.base.IFXNewsPersistent;
import com.redlips.spring.features.pojo.FXNewsBean;

/**
 * @author qiaotong
 * @create 2019-02-15 9:48
 * @description 演示FXNewsBean的作用域为prototype时，获取的bean却是同一个。解决这个问题的关键就是保证getNewsBean()方法每次从容器取得的
 * 都是新的FXNewsBean实例：
 * 1.方法注入-lookup-method属性
 * 2.让MockNewsPersistent获取BeanFactory的引用，通过实现BeanFactoryAware接口，获取BeanFactory引用，直接的调用BeanFactory的getBean
 * ("newsBean")方法,eg:MockNewsPersistent2
 * 3.使用ObjectFactoryCreatingFactoryBean获取ObjectFactory,该对象是特定的与Spring容器交互的一个实现,好处是隔离了客户端对象与BeanFact
 * ory的直接引用,eg:MockNewsPersistent3
 */
public class MockNewsPersistent implements IFXNewsPersistent {
    private FXNewsBean newsBean;

    @Override
    public FXNewsBean getNewsByPK(String newsId) {
        return null;
    }

    @Override
    public void persistNews(FXNewsBean newsBean) {

    }

    public void persistNews() {
        System.out.println("persist bean:" + getNewsBean());
    }

    public FXNewsBean getNewsBean() {
        return newsBean;
    }

    public void setNewsBean(FXNewsBean newsBean) {
        this.newsBean = newsBean;
    }
}
