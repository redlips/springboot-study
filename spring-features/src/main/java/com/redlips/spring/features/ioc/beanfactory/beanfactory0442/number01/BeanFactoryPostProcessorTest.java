package com.redlips.spring.features.ioc.beanfactory.beanfactory0442.number01;

import com.redlips.spring.features.ioc.beanfactory.beanfactory0442.BasicDateSource;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

/**
 * @author qiaotong
 * @create 2019-02-22 10:48
 * @description 容器扩展机制，在IOC容器初始化后，Bean实例化之前，所做的后置操作(对BeanDefinition所做的额外操作).PropertyPlaceholderConfigurer
 * 允许配置文件中有占位符的存在，并将这些占位符所代表的资源单独配置到简单的properties文件中。基础的机制是当BeanFactory在第一阶段完成所有配置完成
 * 后，BeanFactory保存的对象属性信息还只是以占位符的形式存在，eg:${url}.当PropertyPlaceholderConfigurer作为BeanFactoryPostProcessor
 * 被应用时，它会使用properties中的信息来替换掉BeanDefinition中占位符所表示的属性值.
 */
public class BeanFactoryPostProcessorTest {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        bindViaXmlFile(beanFactory);

        BasicDateSource basicDateSource = (BasicDateSource) beanFactory.getBean("dataSource");
        System.out.println(basicDateSource);
    }

    public static BeanFactory bindViaXmlFile(BeanDefinitionRegistry registry) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
        reader.loadBeanDefinitions("new-config-beanfactory0442.xml");

        // 声明要使用的BeanFactoryPostProcessor
        PropertyPlaceholderConfigurer propertyPostProcessor = new PropertyPlaceholderConfigurer();
        propertyPostProcessor.setLocation(new ClassPathResource("jdbc.properties"));
        propertyPostProcessor.setBeanName("PostProcessor Of ");

        // 执行后处理操作
        propertyPostProcessor.postProcessBeanFactory((DefaultListableBeanFactory) registry);

        return (BeanFactory) registry;
    }
}
