package com.redlips.multithreading.test20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author qiaotong
 * @create 2019-07-12 9:27
 * @description
 */
public class Test {
    public static void main(String[] args) {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        Iterator<Integer> iterator = list.iterator();

        synchronized (list) {

            for (int i = 0; i < 5; i++) {
                new Thread(() -> {
                    while (iterator.hasNext()) {
                        System.out.println(iterator.next());
                    }
                }).start();
            }
        }

        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.toArray();

        ConcurrentSkipListMap<String, Object> skipListMap = new ConcurrentSkipListMap<>();
    }
}
