package com.redlips.data.structure.stack;


/**
 * @author qiaotong
 * @create 2019-01-30 17:13
 * @description 基于链表实现的栈
 */
public class StackBasedLinkedList {
    private Node top = null;

    /**
     * 压栈
     */
    public void push(int value) {
        Node newNode = new Node(value, null);
        // 判断是否是空栈
        if (top == null) {
            top = newNode;
        } else {
            newNode.next = top;
            top = newNode;
        }
    }

    /**
     * 出栈, -1表示没数据
     */
    public int pop() {
        if (top == null) return -1;
        int value = top.data;
        top = top.next;
        return value;
    }

    public void printAll() {
        Node p = top;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }

    private static class Node {
        private int data;
        private Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public int getData() {
            return data;
        }
    }
}
