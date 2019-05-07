package com.redlips.spring.features.ioc.beanfactory.beanfactory0442.number03;

import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qiaotong
 * @create 2019-03-04 17:06
 * @description
 */
public class RunDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("beanfactory0442number03.xml");

        // 通过CustomEditorConfigurer将刚实现的DatePropertyEditor注册到容器中，以告知容器按照DatePropertyEditor的形式进行String到
        // LocalDate的转换
        CustomEditorConfigurer configurer = new CustomEditorConfigurer();
        Map map = new HashMap();
        DatePropertyEditor datePropertyEditor = new DatePropertyEditor();
        datePropertyEditor.setDatePattern("yyyy/MM/dd");
        map.put(LocalDate.class,  datePropertyEditor);

        configurer.setCustomEditors(map);
        configurer.postProcessBeanFactory(beanFactory);

        LocalDateFoo localDateFoo = (LocalDateFoo) beanFactory.getBean("localDateFoo");
        System.out.println(localDateFoo);

    }
}
