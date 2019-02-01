package com.redlips.data.structure;

import com.redlips.data.structure.stack.StackBasedLinkedList;

/**
 * @author qiaotong
 * @create 2019-01-31 13:41
 * @description
 */
public class TestDemo {
    public static void main(String[] args) {
        StackBasedLinkedList stackBasedLinkedList = new StackBasedLinkedList();

        for (int i = 0; i < 9; i++) {
            stackBasedLinkedList.push(i);
        }

        System.out.println(stackBasedLinkedList.pop());
        stackBasedLinkedList.printAll();
    }
}
