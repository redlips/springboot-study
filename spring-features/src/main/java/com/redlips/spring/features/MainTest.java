package com.redlips.spring.features;

import com.redlips.spring.features.ioc.beanfactory.MockDemoObject;
import com.redlips.spring.features.ioc01.FXNewsProvider;
import com.redlips.spring.features.ioc01.service.impl.DowJonesNewsListener;
import com.redlips.spring.features.ioc01.service.impl.DowJonesNewsPersistent;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;

/**
 * @author 花落孤忆
 * @create 2018-11-27 11:48
 * @description
 */
public class MainTest {
        public static void main(String[] args) {
            DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();//构造工厂
//            1.直接编码的方式
//            BeanFactory container = bindViaCode(beanRegistry);
//            2.通过配置文件方式
//            BeanFactory container = bindViaPropertiesFile(beanRegistry);
//            3.通过XML
//            BeanFactory container = bindViaXMLFile(beanRegistry);

//            FXNewsProvider newsProvider = (FXNewsProvider) container.getBean("djNewsProvider");
//            newsProvider.getAndPersistNews();


//            4.通过注解的方式
//            BeanFactory container = bindViaXMLFile(beanRegistry);
//            FXNewsProvider bean = (FXNewsProvider) container.getBean("provider");
//            System.out.println(bean.getNewsListenerBeanName());
//            bean.getAndPersistNews();

//            4.通过注解的方式
            BeanFactory container = bindViaXMLFile(beanRegistry);
            MockDemoObject bean = (MockDemoObject) container.getBean("mockDemo");
            System.out.println(bean.getParam2());
            Map mapping = bean.getMapping();
            System.out.println(mapping.toString());
        }

    /**
     * 1.直接编码方式
     */
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


    /**
     * 2.配置文件方式Properties
     */
    public static BeanFactory bindViaPropertiesFile(BeanDefinitionRegistry registry) {
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(registry);
        reader.loadBeanDefinitions("binding-config.properties");
        return (BeanFactory) registry;
    }

    /**
     * 3.通过配置文件XML
     */
    public static BeanFactory bindViaXMLFile(BeanDefinitionRegistry registry) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
        reader.loadBeanDefinitions("new-config.xml");
        return (BeanFactory) registry;

        // 或者直接,不过已经过时
//        return XmlBeanFactory(new ClassPathResource("new-config.xml"));
    }

}
