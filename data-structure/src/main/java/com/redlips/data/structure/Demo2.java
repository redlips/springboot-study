package com.redlips.data.structure;

/**
 * @author qiaotong
 * @create 2019-01-30 9:31
 * @description 简单Demo,哨兵模式
 */
public class Demo2 {
    public static void main(String[] args) {
        int[] arr = {4, 2, 3, 5, 9, 6};

        System.out.println(System.currentTimeMillis());
        System.out.println(find(arr, 6, 6));
        System.out.println(System.currentTimeMillis());
    }

    private static int find(int[] intArr, int n, int key) {
        // 首先处理边界问题
        if (intArr == null || n <= 0)
            return -1;

        // 这里因为要将 intArr[n-1] 的值替换成 key，所以要特殊处理这个值
        if (intArr[n - 1] == key)
            return n - 1;

        // 把 intArr[n-1] 的值临时保存在变量 temp 中，以便之后恢复
        // 把key的值放入intArr[n-1]中
        int temp = intArr[n - 1];
        intArr[n - 1] = key;

        int i = 0;
        // while 循环比起代码一，少了 i<n的操作
        while (intArr[i] != key) ++i;

        // 恢复原来的数组
        intArr[n - 1] = temp;
        if (i == n - 1)
            return -1;
        else
            return i;
    }
}
