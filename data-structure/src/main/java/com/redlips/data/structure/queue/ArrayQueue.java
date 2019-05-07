package com.redlips.data.structure.queue;

/**
 * @author qiaotong
 * @create 2019-03-01 9:11
 * @description 基于数组的队列
 */
public class ArrayQueue {
    // 数组items. 数组大小n
    private String[] items;
    private int n;
    // head队头下标。tail队尾下标
    private int head = 0;
    private int tail = 0;

    // 申请一个大小为capacity的数组
    public ArrayQueue(int capacity) {
        items = new String[capacity];
        n = capacity;
    }

    // 入队
    public boolean enqueue(String item) {
        // tail == n 表示队列已经满了
        if (tail == n) {
            // tail == n && head == 0 表示整个队列都占满了
            if (head == 0) return false;
            // 数据搬移
            for (int i = head; i < tail; i++) {
                items[i - head] = items[i];
            }
            // 搬移完之后，更新head和tail
            tail -= head;
            head = 0;
        }
        items[tail] = item;
        ++tail;
        return true;
    }

    // 出队
    public String dequeue() {
        // tail == head 表示队列为空
        if (tail == head) return null;
        String result = items[head];
        ++head;

        return result;
    }
}
