package com.redlips.java.features.design.pattern.proxy._1;

import com.redlips.java.features.design.pattern.proxy.IUserDao;

/**
 * @author qiaotong
 * @create 2019-06-13 9:14
 * @description
 */
public class UserDaoProxy implements IUserDao {
    private IUserDao targetDao;

    public UserDaoProxy(IUserDao iUserDao) {
        this.targetDao = iUserDao;
    }

    @Override
    public void save() {
        System.out.println("开启事务");
        targetDao.save();
        System.out.println("提交事务");
    }
}
