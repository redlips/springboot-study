package com.redlips.abstractQueueS;

import java.io.Serializable;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author qiaotong
 * @create 2019-01-21 13:47
 * @description
 */
public class Mutex implements Serializable {
    // 静态内部类 继承AQS
    private static class Sync extends AbstractQueuedSynchronizer {
        /**
         * 是否处于占用状态
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        /**
         * 当状态为0获取锁，CAS操作成功，把状态置为1
         */
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 释放锁，将同步状态置为0
         */
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0)
                throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }

    private final Sync sync = new Sync();

    /**
     * 加锁操作，代理到acquire模板方法上就行，acquire会调用重写的tryAcquire;
     */
    public void lock() {
        sync.acquire(1);
    }
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    /**
     * 释放锁
     */
    public void unLock() {
        sync.release(1);
    }
    public boolean isLocked() {
        return sync.isHeldExclusively();
    }
}
