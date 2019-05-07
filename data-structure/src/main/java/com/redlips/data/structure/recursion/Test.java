package com.redlips.data.structure.recursion;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qiaotong
 * @create 2019-03-04 9:33
 * @description
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("result:" + f(10));
    }

    public static Map<Long, Long> map = new HashMap<>();
    public static long f(long n) {
        if (n < 0) return -1;
        if (n == 1) return 1;
        if (n == 2) return 2;

        if (map.containsKey(n)) {
            return map.get(n);
        }
        long temp = f(n - 1) + f(n -2);
        map.put(n, temp);
        return temp;
    }
}
