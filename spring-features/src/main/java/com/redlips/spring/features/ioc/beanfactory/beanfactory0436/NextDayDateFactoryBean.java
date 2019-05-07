package com.redlips.spring.features.ioc.beanfactory.beanfactory0436;

import org.springframework.beans.factory.FactoryBean;

import java.time.LocalDate;

/**
 * @author qiaotong
 * @create 2019-02-14 16:28
 * @description 每次得到的日期都是第二天，在用XML配置注册Bean的时候，通过通常的id引用，容器返回的是FactoryBean所“生产”的对象类型，而非Fact
 * oryBean本身
 */
public class NextDayDateFactoryBean implements FactoryBean {
    @Override
    public Object getObject() {
        return LocalDate.now().plusDays(1);
    }

    @Override
    public Class<?> getObjectType() {
        return LocalDate.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
