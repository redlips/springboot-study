package com.redlips.java.features.jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qiaotong
 * @create 2019-05-13 17:03
 * @description
 */
public class VolatileMemoryReordering {
    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();
        new Thread(() -> config.init()).start();
        new Thread(() -> config.doSomething()).start();
    }

    static class Config {
        private Map<String, String> confMap = null;
        private boolean initialized = false;

        public void init() {
            confMap = new HashMap();
            confMap.put("1", "初始化完毕");
            initialized = true;
        }

        public void doSomething() {
            while (!initialized) {
                try {
                    String value = confMap.get("1");
                    System.out.println("获取到了初始化的值 value = {}" + value);
                } catch (NullPointerException e) {
                    System.out.println("发生指令重排问题");
                    return;
                }
            }
        }
    }
}
