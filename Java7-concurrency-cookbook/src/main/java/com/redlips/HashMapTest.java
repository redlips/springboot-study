package com.redlips;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author qiaotong
 * @create 2019-06-24 9:07
 * @description
 */
public class HashMapTest {

    public static void main(String[] args) {
        Map<Integer, Integer> linkHashMap = new LinkedHashMap<>(10, 0.75f, true);
        HashMap<Integer, String> map = new HashMap<>(16);
        map.put(0, "");
        map.put(7, "");
        map.put(11, "");
        map.put(43, "");
        map.put(59, "");
        map.put(19, "");
        map.put(3, "");
        map.put(35, "");

        linkHashMap.put(1, 1);
        linkHashMap.put(2, 2);
        linkHashMap.put(3, 3);
        linkHashMap.put(4, 4);
        linkHashMap.put(5, 5);
        linkHashMap.put(6, 6);
        linkHashMap.put(7, 7);
        linkHashMap.put(8, 8);
        linkHashMap.put(9, 9);
        linkHashMap.put(10, 10);
        linkHashMap.put(11, 11);
        linkHashMap.put(97, 11);
        linkHashMap.put(98, 11);

        for (Integer key : linkHashMap.keySet()) {
            System.out.print(key + "->");
        }
    }


}
