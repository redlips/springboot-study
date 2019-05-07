package com.redlips.spring.features.ioc.beanfactory.beanfactory0436;

import java.time.LocalDate;

/**
 * @author qiaotong
 * @create 2019-02-14 16:35
 * @description 此处声明的依赖dateOfNextDay是LocalDate类型，而不是NextDayDateFactoryBean类型
 */
public class NextDayDateDisplayer {
    private LocalDate dateOfNextDay;

    public LocalDate getDateOfNextDay() {
        return dateOfNextDay;
    }

    public void setDateOfNextDay(LocalDate dateOfNextDay) {
        this.dateOfNextDay = dateOfNextDay;
    }
}
