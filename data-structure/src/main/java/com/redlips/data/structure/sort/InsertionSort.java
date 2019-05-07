package com.redlips.data.structure.sort;

/**
 * @author qiaotong
 * @create 2019-03-14 9:04
 * @description
 */
public class InsertionSort {
    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 0};
        printArr(arr);
        printArr(insertionSort(arr, arr.length));
    }

    public static int[] insertionSort(int[] arr, int n) {
        if (n <= 1) return arr;

        for (int i = 1; i < n; i++) {
            int value = arr[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (arr[j] > value) {
                    // 数据移动
                    System.out.println("数据移动时的j = " + j);
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            // 插入数据
            System.out.println("插入数据时的j = " + j);
            arr[j + 1] = value;
        }
        return arr;
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
