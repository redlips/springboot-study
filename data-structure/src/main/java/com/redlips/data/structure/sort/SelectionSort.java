package com.redlips.data.structure.sort;

/**
 * @author qiaotong
 * @create 2019-03-15 8:48
 * @description 选择排序
 */
public class SelectionSort {
    public static void main(String[] args) {
        int[] arr = {1, 3, 1, 5, 8, 2, 7, 11, 23, 9, 9};
        printArr(arr);
        printArr(selectionSort(arr, arr.length));
    }

    public static int[] selectionSort(int[] arr, int n) {
        for (int i = 0; i < n; i++) {
            int min = arr[i];
            int minIndex = i;
            for (int j = i; j < n - 1; j++) {
                if (min > arr[j + 1]) {
                    min = arr[j + 1];
                    minIndex = j + 1;
                }
            }
            arr[minIndex] = arr[i];
            arr[i] = min;
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
