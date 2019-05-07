package com.redlips.spring.features.ioc.beanfactory.beanfactory0442.number02;

import com.redlips.spring.features.ioc.beanfactory.beanfactory0442.BasicDateSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author qiaotong
 * @create 2019-02-22 10:48
 * @description 容器(ApplicationContext容器)扩展机制，在IOC容器初始化后，Bean实例化之前，所做的后置操作(对BeanDefinition所做的额外操作)
 */
public class ApplicationPostProcessorTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("new-config-applicationcontext0442.xml");
        BasicDateSource dataSource = (BasicDateSource) context.getBean("dataSource");
        System.out.println(dataSource);
    }

}
