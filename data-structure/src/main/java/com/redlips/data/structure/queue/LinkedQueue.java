package com.redlips.data.structure.queue;

/**
 * @author qiaotong
 * @create 2019-03-01 11:49
 * @description 基于链表的队列
 */
public class LinkedQueue {
    // 队列的队首和队尾
    private Node head;
    private Node tail;

    /**
     * 入队
     */
    public void enqueue(String value) {
        if (tail == null) {
            Node temp = new Node(value, null);
            head = temp;
            tail = temp;
        } else {
            tail.next = new Node(value, null);
            tail = tail.next;
        }
    }

    /**
     * 出队
     */
    public String dequeue() {
        if (head == null) return null;

        String value = head.value;
        head = head.next;
        if (head == null) tail = null;

        return value;
    }

    public void printAll() {
        Node p = head;
        while (p != null) {
            System.out.println(p.value + " ");
            p = head.next;
        }
    }


    private class Node {
        private String value;
        private Node next;

        public Node(String value, Node next) {
            this.value = value;
            this.next = next;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
