package com.redlips.multithreading.test17;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author qiaotong
 * @create 2019-07-08 11:31
 * @description 读写锁 快速实现一个缓存，读多写少
 */
public class Cache<K, V> {
    final Map<K, V> map = new HashMap<>();
    final ReadWriteLock rwl = new ReentrantReadWriteLock();
    // 读锁
    final Lock r = rwl.readLock();
    // 写锁
    final Lock w = rwl.writeLock();

    // 读缓存
    V get(K key) {
        V v = null;
        r.lock();
        try {
            v = map.get(key);
        } finally {
            r.unlock();
        }

        // 缓存中存在，返回
        if (v != null)
            return v;

        // 缓存不存在，查询数据库
        w.lock();
        try {
            // 再次验证，其它线程可能已经查询过数据库，并写入
            v = map.get(key);
            if (v == null) {
                // 查询数据库，此处省略代码无数
//                v = xx;
                map.put(key, v);
            }
        } finally {
            w.unlock();
        }
        return v;
    }

    // 写缓存
    V put(K key, V v) {
        w.lock();
        try {
            return map.put(key, v);
        } finally {
            w.unlock();
        }
    }
}
