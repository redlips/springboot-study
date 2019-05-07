package com.redlips.spring.features.ioc.beanfactory.beanfactory0431;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author 花落孤忆
 * @create 2018-12-06 9:05
 * @description BeanFactory Demo, XML配置中bean标签的讲解
 */
public class MockDemoObject {
    private List param1;
    private String[] param2;
    private Set valueSet;
    private Map mapping;
    private Properties emailAddrs;

    public Properties getEmailAddrs() {
        return emailAddrs;
    }

    public void setEmailAddrs(Properties emailAddrs) {
        this.emailAddrs = emailAddrs;
    }

    public Map getMapping() {
        return mapping;
    }

    public void setMapping(Map mapping) {
        this.mapping = mapping;
    }

    public Set getValueSet() {
        return valueSet;
    }

    public void setValueSet(Set valueSet) {
        this.valueSet = valueSet;
    }

    public List getParam1() {
        return param1;
    }

    public void setParam1(List param1) {
        this.param1 = param1;
    }

    public String[] getParam2() {
        return param2;
    }

    public void setParam2(String[] param2) {
        this.param2 = param2;
    }
}
