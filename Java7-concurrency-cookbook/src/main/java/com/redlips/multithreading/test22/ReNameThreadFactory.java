package com.redlips.multithreading.test22;

import com.sun.istack.internal.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qiaotong
 * @create 2019-07-15 16:42
 * @description 线程池工厂，重命名线程名
 */
public class ReNameThreadFactory implements ThreadFactory {
    /** 线程池编号， 容器中所有线程池的数量 */
    private static final AtomicInteger POOLNUMBER = new AtomicInteger(1);
    /** 线程编号， 当前线程池线程的数量 */
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    /** 线程组 */
    private final ThreadGroup group;
    /** 业务名称前缀 */
    private final String namePrefix;

    /** 重写线程名称（获取线程池编号、线程编号、线程组） */
    public ReNameThreadFactory(@NotNull String prefix) {
        SecurityManager securityManager = System.getSecurityManager();
        group = (securityManager != null) ? securityManager.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        // 组装前缀
        namePrefix = prefix + "-poolNumber" + POOLNUMBER.getAndIncrement() + "-threadNumber";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
