package com.redlips.spring.features;

import com.redlips.spring.features.base.FXNewsProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author qiaotong
 * @create 2019-02-20 16:26
 * @description
 */
public class MethodReplacerTest {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();//构造工厂
        BeanFactory beanFactory = bindViaXMLFile(beanRegistry);
        FXNewsProvider djNewsProvider = (FXNewsProvider) beanFactory.getBean("djNewsProvider");
        djNewsProvider.getAndPersistNews();
    }

    public static BeanFactory bindViaXMLFile(BeanDefinitionRegistry registry) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
        reader.loadBeanDefinitions("new-config-number02.xml");
        return (BeanFactory) registry;
    }
}
