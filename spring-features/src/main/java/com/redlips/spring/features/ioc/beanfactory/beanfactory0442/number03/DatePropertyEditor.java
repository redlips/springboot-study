package com.redlips.spring.features.ioc.beanfactory.beanfactory0442.number03;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author qiaotong
 * @create 2019-02-28 17:47
 * @description PropertyEditor是对Bean定义中属性类型转换的编辑，Spring容器中提供了多种默认的PropertyEditor对数据类型进行转换，而自定义
 * 的类型需要通过实现PropertyEditor，并通过CustomEditorConfigurer告知容器。
 *
 * 给出针对特定对象类型的PropertyEditor实现
 * 如果仅仅支持单向的从String到相应对象类型的转换，只要覆盖方法setAsText，如果支持双向转换，需要同时考虑getAsText方法的覆写
 */
public class DatePropertyEditor extends PropertyEditorSupport {
    private String datePattern;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getDatePattern());
        LocalDate parse = LocalDate.parse(text, formatter);
        setValue(parse);
    }

    public String getDatePattern() {
        return this.datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }
}
