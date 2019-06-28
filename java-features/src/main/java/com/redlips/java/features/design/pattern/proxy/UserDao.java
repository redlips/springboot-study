package com.redlips.java.features.design.pattern.proxy;

/**
 * @author qiaotong
 * @create 2019-06-13 9:12
 * @description
 */
public class UserDao implements IUserDao {
    public void save() {
        System.out.println("保存成功");
    }
}
