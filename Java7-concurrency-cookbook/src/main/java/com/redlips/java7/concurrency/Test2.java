package com.redlips.java7.concurrency;

/**
 * @author qiaotong
 * @create 2019-01-24 14:30
 * @description
 */
public class Test2 {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        ListNode result = new ListNode(0);
        result.val = 2;
        System.out.println(result.val);
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode tempNode = result;
        ListNode node1 = l1;
        ListNode node2 = l2;
        int temp = 0;
        while (node1 != null || node2 != null) {
            int value1;
            int value2;
            int sum;
            value1 = node1 == null ? 0 : node1.val;
            value2 = node2 == null ? 0 : node2.val;

            sum = temp + value1 + value2;
            temp = sum / 10;

            tempNode.next = new ListNode(sum % 10);
            tempNode = tempNode.next;
            if (node1 != null)
                node1 = node1.next;
            if (node2 != null)
                node2 = node2.next;
        }
        if (temp > 0) {
            tempNode.next = new ListNode(temp);
        }

        return result.next;
    }
}
