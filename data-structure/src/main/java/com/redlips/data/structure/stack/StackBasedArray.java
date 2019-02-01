package com.redlips.data.structure.stack;

/**
 * @author qiaotong
 * @create 2019-01-30 16:05
 * @description 基于数组的顺序栈
 */
public class StackBasedArray {
    private String[] items; // 数据
    private int count;  // 栈中元素的个数
    private int n;  // 栈的大小

    public StackBasedArray(int n) {
        this.items = new String[n];
        this.n = n;
        this.count = 0;
    }

    /**
     * 入栈操作
     */
    public boolean push(String item) {
        // 数组空间不够，入栈失败
        if (count == n) return false;

        items[count] = item;
        ++count;
        return true;
    }

    public String pop() {
        // 栈为空，直接返回null
        if (count == 0) return null;
        // 返回下表为count - 1的元素，并将元素的格式-1
        String temp = items[count - 1];
        --count;
        return temp;
    }
}
