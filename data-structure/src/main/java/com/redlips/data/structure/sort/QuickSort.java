package com.redlips.data.structure.sort;

/**
 * @author qiaotong
 * @create 2019-03-21 14:57
 * @description 快速排序、快排
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {1, 3, 1, 5, 8, 2, 7, 11, 23, 9, 9};
        printArr(arr);
        quickSort(arr, arr.length);
        printArr(arr);
    }

    private static void quickSort(int[] arr, int length) {
        quickSortExec(arr, 0, length - 1);
    }

    private static void quickSortExec(int[] arr, int p, int r) {
        // 退出递归条件
        if (p >= r) return;

        // 获取分区点的下标
        int q = partition(arr, p, r);

        // 分治，递归
        quickSortExec(arr, p, q - 1);
        quickSortExec(arr, q + 1, r);
    }

    private static int partition(int[] arr, int p, int r) {
        return 0;
    }

    public static void printArr(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) System.out.print(arr[i]);
            else System.out.print("," + arr[i]);
        }
        System.out.println("]");
    }
}
