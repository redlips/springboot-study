package com.redlips.data.structure.queue;

/**
 * @author qiaotong
 * @create 2019-03-02 11:18
 * @description 循环队列，省去了数据搬移的操作
 */
public class CircularQueue {
    // 数组：items,n：数组大小
    private String[] items;
    private int n;
    // head头下标，tail尾下标
    private int head = 0;
    private int tail = 0;

    // 申请一个大小为capacity的循环队列
    public CircularQueue(int capacity) {
        items = new String[capacity];
        n = capacity;
    }

    // 入队
    public boolean enqueue(String item) {
        // 队列满了
        if ((tail + 1) % n == head) return false;
        items[tail] = item;
        tail = (tail + 1) % n;
        return true;
    }

    // 出队
    public String dequeue() {
        // 如果tail == head表示队列为空
        if (tail == head) return null;
        String value = items[head];
        head = (head + 1) % n;
        return value;
    }
}
