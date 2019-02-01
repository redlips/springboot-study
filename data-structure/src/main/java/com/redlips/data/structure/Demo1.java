package com.redlips.data.structure;

/**
 * @author qiaotong
 * @create 2019-01-30 9:31
 * @description 简单Demo,无哨兵模式
 */
public class Demo1 {
    public static void main(String[] args) {
        int[] arr = {4, 2, 3, 5, 9, 6};

        System.out.println(System.currentTimeMillis());
        System.out.println(find(arr, 6, 7));
        System.out.println(System.currentTimeMillis());
    }

    private static int find(int[] intArr, int n, int key) {
        // 首先处理边界问题
        if (intArr == null || n <= 0)
            return -1;

        int i = 0;
        while (i < n) {
            if (intArr[i] == key)
                return i;
            ++i;
        }

        return -1;
    }
}
