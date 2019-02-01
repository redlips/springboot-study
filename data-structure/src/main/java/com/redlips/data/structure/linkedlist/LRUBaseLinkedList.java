package com.redlips.data.structure.linkedlist;

/**
 * @author qiaotong
 * @create 2019-01-31 13:54
 * @description 基于单链表的最近最少使用算法
 */
public class LRUBaseLinkedList<T> {
    /**
     * 默认链表容量
     */
    private final static Integer DEFAULT_CAPACITY = 10;
    /**
     * 头结点
     */
    private SNode<T> headNode;

    /**
     * 链表长度
     */
    private Integer length;

    /**
     * 链表容量
     */
    private Integer capacity;

    public LRUBaseLinkedList() {
        this.headNode = new SNode<>();
        this.capacity = DEFAULT_CAPACITY;
        this.length = 0;
    }

    public LRUBaseLinkedList(Integer capacity) {
        this.headNode = new SNode<>();
        this.capacity = capacity;
        this.length = 0;
    }

    public void add(T data) {
        // 查找前一个节点
        SNode preNode = findPreNode(data);

        // 链表中存在，删除原数据，再插入到头部
        if (preNode != null) {
            deleteElemOptim(preNode);
            intsertEleAtBegin(data);
        } else {
            if (length >= this.capacity)
                // 删除尾节点
                deleteEleAtEnd();
            intsertEleAtBegin(data);
        }
    }

    /**
     * 删除preNode节点的下一个元素
     */
    private void deleteElemOptim(SNode preNode) {
        SNode temp = preNode.getNext();
        preNode.setNext(temp.getNext());
        temp = null;
        length--;
    }

    /**
     * 获取查找到元素的前一个结点
     */
    private SNode findPreNode(T data) {
        SNode node = headNode;
        while (node.getNext() != null) {
            if (data.equals(node.getNext().getElement()))
                return node;
            node = node.getNext();
        }
        return null;
    }

    /**
     * 链表头部插入节点
     */
    private void intsertEleAtBegin(T data) {
        SNode<T> next = headNode.getNext();
        headNode.setNext(new SNode<>(data, next));
        length++;
    }

    /**
     * 删除尾节点
     */
    private void deleteEleAtEnd() {
        SNode ptr = headNode;
        // 空链表直接返回
        if (headNode.getNext() == null) return;

        // 遍历直至倒数第二个节点
        while (ptr.getNext().getNext() != null) {
            ptr = ptr.getNext();
        }

        // 待删除的尾节点
        SNode end = ptr.getNext();
        ptr.setNext(null);
        end = null;

        length--;
    }


    public class SNode<T> {
        private T element;
        private SNode<T> next;

        public SNode() {
            this.next = null;
        }

        public SNode(T element) {
            this.element = element;
        }

        public SNode(T element, SNode<T> next) {
            this.element = element;
            this.next = next;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public SNode<T> getNext() {
            return next;
        }

        public void setNext(SNode<T> next) {
            this.next = next;
        }
    }
}
