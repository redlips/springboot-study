package com.redlips.spring.features;

import com.redlips.spring.features.ioc01.FXNewsProvider;
import com.redlips.spring.features.ioc01.service.impl.DowJonesNewsListener;
import com.redlips.spring.features.ioc01.service.impl.DowJonesNewsPersistent;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

/**
 * @author 花落孤忆
 * @create 2018-11-27 11:48
 * @description
 */
public class MainTest {
        public static void main(String[] args) {
            DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();//构造工厂
            BeanFactory container = bindViaCode(beanRegistry);
            FXNewsProvider newsProvider = (FXNewsProvider) container.getBean("djNewsProvider");
            newsProvider.getAndPersistNews();

        }

        public static BeanFactory bindViaCode(BeanDefinitionRegistry  registry) {
            AbstractBeanDefinition newsProvider = new RootBeanDefinition(FXNewsProvider.class);
            AbstractBeanDefinition newsListener = new RootBeanDefinition(DowJonesNewsListener.class);
            AbstractBeanDefinition newsPersistent = new RootBeanDefinition(DowJonesNewsPersistent.class);

            // 将Bean定义注册到容器中
            registry.registerBeanDefinition("djNewsProvider", newsProvider);
            registry.registerBeanDefinition("djNewsListener", newsListener);
            registry.registerBeanDefinition("djNewsPersistent", newsPersistent);

            // 指定依赖关系
            // 1.通过构造方法注入方式
            ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
            argumentValues.addIndexedArgumentValue(0, newsListener);
            argumentValues.addIndexedArgumentValue(1, newsPersistent);
            newsProvider.setConstructorArgumentValues(argumentValues);
            // 2.或者通过setter方法注入方式
            MutablePropertyValues propertyValues = new MutablePropertyValues();
            propertyValues.addPropertyValue(new PropertyValue("newsListener", newsListener));
            propertyValues.addPropertyValue(new PropertyValue("newsPersistent", newsPersistent));
            newsProvider.setPropertyValues(propertyValues);

            // 绑定完成
            return (BeanFactory) registry;
        }
}
